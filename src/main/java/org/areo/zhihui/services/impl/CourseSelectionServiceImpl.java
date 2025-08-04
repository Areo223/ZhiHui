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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseSelectionServiceImpl implements CourseSelectionService {
    private final CourseCacheService courseCacheService;
    private final CourseOfferingMapper courseOfferingMapper;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    public Result<Void> selectCourse(String studentIdentifier, String courseOfferingId){
        //检查是否已经选课
        if (courseCacheService.isStudentInCourse(courseOfferingId,studentIdentifier)){
            throw new CommonException("已选过该课程");
        }

        //获取分布式锁
        boolean locked = courseCacheService.tryLock(courseOfferingId,30);
        if(!locked){
            throw new CommonException("系统繁忙,请稍后重试");
        }

        try {
            //检查库存是否有余量
            Integer stock = courseCacheService.getCourseStock(courseOfferingId);
            if(stock == null){
                //从数据库中加载数据
                try {
                    CourseOffering courseOffering = courseOfferingMapper.selectOne(new QueryWrapper<CourseOffering>().eq("id", courseOfferingId));
                    stock = courseOffering.getCurrentCapacity();
                    courseCacheService.initCourseStockCache(courseOfferingId,stock);
                }catch (Exception e){
                    throw e;
                }
            }

            if(stock <= 0){
                throw new CommonException("课程已满");
            }

            //扣减库存
            courseCacheService.reduceCourseStock(courseOfferingId);
            courseCacheService.addStudentToCourse(courseOfferingId,studentIdentifier);


            //异步写入数据库
            asyncSaveSelection(studentIdentifier,courseOfferingId);

        }finally {
            //释放锁
            courseCacheService.releaseLock(courseOfferingId);
        }
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
            enrollmentMapper.insert(enrollment);

            //更新选课已选人数
            courseOfferingMapper.reduceCurrentCapacity(courseOfferingId);
        }catch (Exception e){
            log.error("异步保存选课记录失败:{}",e.getMessage());
            //回滚Redis操作
            courseCacheService.increaseCourseStock(courseOfferingId);
            courseCacheService.removeStudentFromCourse(courseOfferingId,studentIdentifier);
            throw e;
        }
    }

    //退课功能
    @Transactional
    public Result<Void> withdrawCourse(String studentIdentifier, String courseOfferingId){
        //检查是否已选课程
        if (!courseCacheService.isStudentInCourse(courseOfferingId,studentIdentifier)){
            throw new CommonException("学生尚未选择该课程");
        }

        //获取分布式锁
        boolean locked = courseCacheService.tryLock(courseOfferingId,30);
        if(!locked){
            throw new CommonException("系统繁忙,请稍后再试");
        }

        //减少库存
        try {
            //检查库存是否有余量
            Integer stock = courseCacheService.getCourseStock(courseOfferingId);
            if(stock == null){
                //从数据库中加载数据
                try {
                    CourseOffering courseOffering = courseOfferingMapper.selectOne(new QueryWrapper<CourseOffering>().eq("id", courseOfferingId));
                    stock = courseOffering.getCurrentCapacity();
                    courseCacheService.initCourseStockCache(courseOfferingId,stock);
                }catch (Exception e){
                    throw e;
                }
            }

            courseCacheService.increaseCourseStock(courseOfferingId);
            courseCacheService.removeStudentFromCourse(studentIdentifier,courseOfferingId);


            //异步写入数据库
            asyncWithdrawSelection(studentIdentifier,courseOfferingId);

        }finally {
            //释放锁
            courseCacheService.releaseLock(courseOfferingId);
        }
        return Result.success(null);
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
        }
    }
}
