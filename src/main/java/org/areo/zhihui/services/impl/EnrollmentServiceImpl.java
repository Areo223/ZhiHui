package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.mapper.CourseOfferingMapper;
import org.areo.zhihui.mapper.EnrollmentMapper;
import org.areo.zhihui.mapper.TeachingClassMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.CourseOffering;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.pojo.entity.TeachingSession;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.pojo.vo.CourseOfferingVO;
import org.areo.zhihui.pojo.vo.TeachingSessionVO;
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
            courseSelectionService.selectCourse(enrollment.getStudentIdentifier(), enrollment.getCourseOfferingId());
        } catch (Exception e) {
            return Result.failure(e);
        }

        return Result.success(null);
    }

    @Override
    public Result<Void> withdrawCourse(Enrollment enrollment) {
        try {
            courseSelectionService.withdrawCourse(enrollment.getStudentIdentifier(), enrollment.getCourseOfferingId());
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
}

