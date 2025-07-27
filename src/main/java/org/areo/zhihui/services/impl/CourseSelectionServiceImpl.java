package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.EnrollmentMapper;
import org.areo.zhihui.mapper.TeachingClassMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.pojo.entity.TeachingClass;
import org.areo.zhihui.services.CourseCacheService;
import org.areo.zhihui.services.CourseSelectionService;
import org.areo.zhihui.utils.enums.SemesterEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseSelectionServiceImpl implements CourseSelectionService {
    private static final Logger log = LoggerFactory.getLogger(CourseSelectionServiceImpl.class);
    private final CourseCacheService courseCacheService;
    private final TeachingClassMapper teachingClassMapper;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    public Result<Void> selectCourse(String studentIdentifier, String teachingClassCode){
        //检查是否已经选课
        if (courseCacheService.isStudentInCourse(teachingClassCode,studentIdentifier)){
            return Result.failure(new CommonException("已选过该课程"));
        }

        //获取分布式锁
        boolean locked = courseCacheService.tryLock(teachingClassCode,30);
        if(!locked){
            return Result.failure(new CommonException("系统繁忙,请稍后重试"));
        }

        try {
            //检查库存是否有余量
            Integer stock = courseCacheService.getCourseStock(teachingClassCode);
            if(stock == null){
                //从数据库中加载数据
                try {
                    TeachingClass teachingClass = teachingClassMapper.selectOne(new QueryWrapper<TeachingClass>().eq("teaching_class_code", teachingClassCode));
                    stock = teachingClass.getCurrentCapacity();
                    courseCacheService.initCourseStockCache(teachingClassCode,stock);
                }catch (Exception e){
                    return Result.failure(e);
                }
            }

            if(stock <= 0){
                return Result.failure(new CommonException("课程已满"));
            }

            //扣减库存
            courseCacheService.reduceCourseStock(teachingClassCode);
            courseCacheService.addStudentToCourse(teachingClassCode,studentIdentifier);


            //异步写入数据库
            asynSaveSelection(studentIdentifier,teachingClassCode);
            return Result.success(null);

        }finally {
            //释放锁
            courseCacheService.releaseLock(teachingClassCode);
        }
    }

    @Async
    public void asynSaveSelection(String studentIdentifier, String teachingClassCode) {
        try {
            // 检查是否已经选过
            boolean exists = enrollmentMapper.isStudentHasSelectedCourse(studentIdentifier,teachingClassCode);
            if(exists){
                //回滚Redis操作
                courseCacheService.increaseCourseStock(teachingClassCode);
                courseCacheService.removeStudentFromCourse(studentIdentifier,teachingClassCode);
                return;
            }

            //插入选课记录
            Enrollment enrollment = new Enrollment();
            enrollment.setSemester(SemesterEnum.semesterSet());
            //TODO:处理课程的id和编号问题
            enrollment.setStudentIdentifier(studentIdentifier);
            enrollment.setTeachingClassCode(teachingClassCode);
            enrollmentMapper.insert(enrollment);

            //更新选课已选人数
            teachingClassMapper.reduceCurrentCapacity(teachingClassCode);
        }catch (Exception e){
            log.error("异步保存选课记录失败:{}",e.getMessage());
            //回滚Redis操作
            courseCacheService.increaseCourseStock(teachingClassCode);
            courseCacheService.removeStudentFromCourse(studentIdentifier,teachingClassCode);
        }
    }
}
