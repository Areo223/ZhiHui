package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.mapper.*;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Class;
import org.areo.zhihui.pojo.entity.*;
import org.areo.zhihui.pojo.vo.CourseVO;
import org.areo.zhihui.pojo.vo.TeachingClassVO;
import org.areo.zhihui.services.CourseService;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final ClassMapper classMapper;
    private final UserMapper userMapper;
    private final EnrollmentMapper enrollmentMapper;
    private final TeachingClassMapper teachingClassMapper;


    @Override
    public Result<Void> addCourse(Course course) {
        log.info("新增课程：{}", course);
        courseMapper.insert(course);
        return Result.success(null);
    }

    @Override
    public Result<Void> deleteCourse(Integer id) {
        log.info("删除课程：{}", id);
        courseMapper.deleteById(id);
        return Result.success(null);
    }

    @Override
    public Result<List<CourseVO>> getCourse() {
        User user = UserHolder.getUser();
        if (user.getRole() == RoleEnum.ADMIN) {
            // 管理员查询所有课程信息
            log.info("管理员{}查询所有课程信息", user.getId());
            List<Course> courseList = courseMapper.selectList(null);
            // 转换为VO
            List<CourseVO> courseVOList = courseList.stream()
                    .map(course -> {
                        CourseVO courseVO = new CourseVO();
                        BeanUtils.copyProperties(course, courseVO);
                        return courseVO;
                    }).toList();
            return Result.success(courseVOList);
        } else if (user.getRole() == RoleEnum.TEACHER) {
            // 教师查询自己的课程信息
            log.info("教师{}查询自己的课程信息", user.getId());
            List<Course> courseList = courseMapper.selectList(new QueryWrapper<Course>().eq("teacher_id", user.getId()));
            // 转换为VO
            List<CourseVO> courseVOList = courseList.stream()
                   .map(course -> {
                        CourseVO courseVO = new CourseVO();
                        BeanUtils.copyProperties(course, courseVO);
                        return courseVO;
                    }).toList();
            return Result.success(courseVOList);
        }
        // 学生查询自己能选择的课程信息
        log.info("学生{}查询自己能选择的课程信息", user.getId());
        //课程面向的是专业年级
        //所以需要在学生表查询学生对应的班级
        Student stu = userMapper.getOwnStudentInfo(user.getId());
        Class stuClass = classMapper.selectById(stu.getClassId());
        //然后查询班级对应的专业和年级

        //然后查询课程表中对应专业年级的课程信息
        List<Course> courseList = courseMapper.selectList(
                new QueryWrapper<Course>().eq("major_id", stuClass.getMajorId())
                        .eq("grade_id", stuClass.getGradeId())
        );
        // 转换为VO
        List<CourseVO> courseVOList = courseList.stream()
              .map(course -> {
                    CourseVO courseVO = new CourseVO();
                    BeanUtils.copyProperties(course, courseVO);
                    return courseVO;
                }).toList();
        return Result.success(courseVOList);


    }

    @Override
    public Result<Void> updateCourse(Course course) {
        log.info("修改课程：{}", course);
        courseMapper.updateById(course);
        return Result.success(null);
    }

    @Override
    public Result<List<TeachingClassVO>> getSelectedCourse() {
        User user = UserHolder.getUser();
        log.info("查询学生选择的课程信息 id{}", user.getId());
        // 要通过查询选课表来获取学生选择的课程信息
        List<Enrollment> enrollments = enrollmentMapper.selectList(new QueryWrapper<Enrollment>().eq("student_id", user.getId()));
        // 遍历选课表，获取课程信息,同时转化为vo
        List<TeachingClassVO> teachingClassVOList = enrollments.stream()
               .map(enrollment -> {
                    TeachingClass teachingClass = teachingClassMapper.selectOne(new QueryWrapper<TeachingClass>().eq("teaching_class_code",enrollment.getTeachingClassCode()));
                   TeachingClassVO teachingClassVO = new TeachingClassVO();
                    BeanUtils.copyProperties(teachingClass, teachingClassVO);
                    return teachingClassVO;
                }).toList();
        return Result.success(teachingClassVOList);

    }
}
