package org.areo.zhihui.servises.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.mapper.ClassMapper;
import org.areo.zhihui.mapper.CourseMapper;
import org.areo.zhihui.mapper.UserMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Class;
import org.areo.zhihui.pojo.entity.Course;
import org.areo.zhihui.pojo.entity.Student;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.servises.CourseService;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.RoleEnum;
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
    public Result<List<Course>> getCourse() {
        log.debug("查询课程开始");
        User user = UserHolder.getUser();
        if (user.getRole() == RoleEnum.ADMIN) {
            // 管理员查询所有课程信息
            log.info("管理员{}查询所有课程信息", user.getId());
            List<Course> courseList = courseMapper.selectList(null);
            return Result.success(courseList);
        } else if (user.getRole() == RoleEnum.TEACHER) {
            // 教师查询自己的课程信息
            log.info("教师{}查询自己的课程信息", user.getId());
            List<Course> courseList = courseMapper.selectList(new QueryWrapper<Course>().eq("teacher_id", user.getId()));
            return Result.success(courseList);
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
        return Result.success(courseList);


    }
}
