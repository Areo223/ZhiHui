package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.CourseOfferingMapper;
import org.areo.zhihui.mapper.EnrollmentMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.CourseOffering;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.services.CourseCacheService;
import org.areo.zhihui.services.CourseSelectionService;
import org.areo.zhihui.services.EnrollmentBatchService;
import org.areo.zhihui.utils.LuaToRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LuaCourseSelectionServiceImpl implements CourseSelectionService {

    private final CourseOfferingMapper courseOfferingMapper;
    private final EnrollmentMapper enrollmentMapper;
    private final CourseCacheService courseCacheService;
    private final LuaToRedis luaToRedis;
    private final EnrollmentBatchService enrollmentBatchService;


    @Override
    @Transactional
    public Result<Void> selectCourse(String studentIdentifier, String courseOfferingId) {
        // 获取课程容量
        Integer capacity = getCourseCapacity(courseOfferingId);
        if (capacity == null) {
           throw new CommonException("课程不存在");
        }

        // 执行Lua脚本
        long result = luaToRedis.executeSelectionScript(
                List.of(
                        "course:stock:" + courseOfferingId,
                        "course:students:" + courseOfferingId
                ),
                List.of(
                        studentIdentifier,
                        capacity.toString()
                )
        );

        // 处理结果
        if (result == 0) {
            throw new CommonException("选课失败");
        }

        //插入选课记录
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentIdentifier(studentIdentifier);
        enrollment.setCourseOfferingId(Integer.parseInt(courseOfferingId));
        enrollmentBatchService.addToBatch(enrollment);

        return Result.success(null);
    }

    @Async
    public void asyncSaveSelection(String studentIdentifier, String courseOfferingId) {
        try {
            // 检查是否已经选过
            boolean exists = enrollmentMapper.checkIfStudentHasSelectedCourse(studentIdentifier,courseOfferingId);
            if(exists){
                //回滚Redis操作
                courseCacheService.increaseCourseStock(courseOfferingId);
                courseCacheService.removeStudentFromCourse(courseOfferingId,studentIdentifier);
                return;
            }

            //插入选课记录
            Enrollment enrollment = new Enrollment();
            enrollment.setStudentIdentifier(studentIdentifier);
            enrollment.setCourseOfferingId(Integer.parseInt(courseOfferingId));
            enrollmentBatchService.addToBatch(enrollment);
        }catch (Exception e){
            log.error("异步保存选课记录失败:{}",e.getMessage());
            //回滚Redis操作
            courseCacheService.increaseCourseStock(courseOfferingId);
            courseCacheService.removeStudentFromCourse(courseOfferingId,studentIdentifier);
            throw e;
        }
    }


    @Override
    @Transactional
    public Result<Void> withdrawCourse(String studentIdentifier, String courseOfferingId) {
        // 获取课程容量
        Integer capacity = getCourseCapacity(courseOfferingId);
        if (capacity == null) {
            throw new CommonException("课程不存在");
        }

        // 执行Lua脚本
        Long result = luaToRedis.executeWithdrawScript(
                List.of(
                        "course:stock:" + courseOfferingId,
                        "course:students:" + courseOfferingId
                ),
                List.of(studentIdentifier, capacity.toString())
        );
        // 处理结果
        if (result == 0) {
            throw new CommonException("选课失败");
        }

        // 异步写入数据库
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentIdentifier(studentIdentifier);
        enrollment.setCourseOfferingId(Integer.parseInt(courseOfferingId));
        enrollmentBatchService.addToDropQueue(enrollment);
        return Result.success(null);
    }

    private Integer getCourseCapacity(String courseOfferingId) {
        Integer capacity = courseCacheService.getCourseMaxCapacity(courseOfferingId);
        if (capacity != null) {
            return capacity;
        }

        // 缓存未命中,查询数据库
        CourseOffering courseOffering = courseOfferingMapper.selectById(courseOfferingId);
        if (courseOffering == null) {
           return null;
        }


        // 初始化缓存
        courseCacheService.initCourseMaxCapacityCache(courseOfferingId, courseOffering.getMaxCapacity());
        courseCacheService.initCourseStockCache(courseOfferingId, courseOffering.getCurrentCapacity());

        // 初始化学生缓存
        List<Enrollment> enrollments = enrollmentMapper.selectList(new QueryWrapper<Enrollment>().eq("course_offering_id", courseOfferingId));
        for (Enrollment enrollment : enrollments) {
            courseCacheService.addStudentToCourse(enrollment.getStudentIdentifier(), courseOfferingId);
        }

        return courseOffering.getMaxCapacity();
    }

    @Async
    public void asyncWithdrawSelection(String studentIdentifier, String courseOfferingId) {
        try {
            // 检查是否已经选过
            boolean exists = enrollmentMapper.checkIfStudentHasSelectedCourse(studentIdentifier,courseOfferingId);
            if(!exists){
                //回滚Redis操作
                courseCacheService.reduceCourseStock(courseOfferingId);
                courseCacheService.addStudentToCourse(studentIdentifier,courseOfferingId);
                return;
            }
            //删除选课记录
            enrollmentMapper.delete(new QueryWrapper<Enrollment>().eq("student_identifier", studentIdentifier)
                    .eq("course_offering_id", courseOfferingId));

            //更新选课已选人数
            courseOfferingMapper.increaseCurrentCapacity(courseOfferingId);

        }catch (Exception e){
            log.error("异步保存退课记录失败:{}",e.getMessage());
            //回滚Redis操作
            courseCacheService.reduceCourseStock(courseOfferingId);
            courseCacheService.addStudentToCourse(studentIdentifier,courseOfferingId);
            throw e;
        }
    }

    @Override
    public void asyncDBToRedis() {
        List<CourseOffering> courseOfferings = courseOfferingMapper.selectList(null);
        // 同步数据到redis
        for (CourseOffering courseOffering : courseOfferings) {
            Integer stock = courseOffering.getCurrentCapacity();
            courseCacheService.initCourseStockCache(courseOffering.getId().toString(),stock);
        }
    }
}
