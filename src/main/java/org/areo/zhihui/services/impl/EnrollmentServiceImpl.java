package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.CourseOfferingMapper;
import org.areo.zhihui.mapper.EnrollmentMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.CourseOffering;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.pojo.vo.CourseOfferingVO;
import org.areo.zhihui.services.CourseSelectionService;
import org.areo.zhihui.services.EnrollmentService;
import org.areo.zhihui.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentMapper enrollmentMapper;
    private final CourseSelectionService courseSelectionService;
    private final CourseOfferingMapper courseOfferingMapper;

    @Override
    public Result<Void> selectCourse(Enrollment enrollment) {
        try {
            courseSelectionService.selectCourse(enrollment.getStudentIdentifier(), enrollment.getCourseOfferingId().toString());
        } catch (Exception e) {
            return Result.failure(e);
        }

        return Result.success(null);
    }

    @Override
    public Result<Void> withdrawCourse(Enrollment enrollment) {
        try {
            courseSelectionService.withdrawCourse(enrollment.getStudentIdentifier(), enrollment.getCourseOfferingId().toString());
        }catch (Exception e){
            return Result.failure(e);
        }
        return Result.success(null);
    }

    @Override
    public Result<List<CourseOfferingVO>> getSelectedCourse() {
        User user = UserHolder.getUser();
        log.info("查询学生选择的课程信息 id{}", user.getId());
        // 要通过查询选课表来获取学生选择的课程信息
        List<Enrollment> enrollments = enrollmentMapper.selectList(new QueryWrapper<Enrollment>().eq("student_identifier", user.getIdentifier()));
        // 遍历选课表，获取课程信息,同时转化为vo
        List<CourseOfferingVO> courseOfferingVOList = enrollments.stream()
                .map(enrollment -> {
                    CourseOffering courseOffering = courseOfferingMapper.selectOne(new QueryWrapper<CourseOffering>().eq("id",enrollment.getCourseOfferingId()));
                    CourseOfferingVO courseOfferingVO = new CourseOfferingVO();
                    BeanUtils.copyProperties(courseOffering, courseOfferingVO);
                    return courseOfferingVO;
                }).toList();
        return Result.success(courseOfferingVOList);

    }

    @Override
    public Result<Enrollment> setGrade(String studentIdentifier, Integer courseOfferingId, Integer grade) {
        Enrollment enrollment = enrollmentMapper.selectOne(
                new QueryWrapper<Enrollment>()
                        .eq("student_identifier", studentIdentifier)
                        .eq("course_offering_id", courseOfferingId));
        if (enrollment == null) {
            return Result.failure(new CommonException("未选择该课程"));
        }
        enrollment.setGrade(grade);
        enrollmentMapper.updateById(enrollment);
        return Result.success(enrollment);
    }

    @Override
    public Result<Enrollment> getGrade(String studentIdentifier, Integer courseOfferingId) {
        // 条件查询,可以为空
        LambdaQueryWrapper<Enrollment> lambdaQueryWrapper = new LambdaQueryWrapper<Enrollment>()
                .eq(studentIdentifier !=null,Enrollment::getStudentIdentifier,studentIdentifier)
                .eq(courseOfferingId !=null,Enrollment::getCourseOfferingId,courseOfferingId);
        Enrollment enrollment = enrollmentMapper.selectOne(lambdaQueryWrapper);
        if (enrollment == null) {
            return Result.failure(new CommonException("不存在该成绩记录"));
        }
        return Result.success(enrollment);
    }

    @Override
    public Result<Enrollment> updGrade(String studentIdentifier, Integer courseOfferingId, Integer grade) {
        Enrollment enrollment = getGrade(studentIdentifier, courseOfferingId).getValue();
        if (enrollment == null) {
            return Result.failure(new CommonException("不存在该成绩记录"));
        }
        enrollment.setGrade(grade);
        enrollmentMapper.updateById(enrollment);
        return Result.success(enrollment);
    }

    @Override
    public Result<Void> asyncDBToRedis() {
        courseSelectionService.asyncDBToRedis();
        return Result.success(null);
    }


}

