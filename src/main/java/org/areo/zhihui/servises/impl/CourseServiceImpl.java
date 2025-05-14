package org.areo.zhihui.servises.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.mapper.CourseMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Course;
import org.areo.zhihui.servises.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceImpl implements CourseService {


    private final CourseMapper courseMapper;

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
    public Result<Course> getCourse(Integer id) {
        log.info("查询课程：{}", id);
        Course course = courseMapper.selectById(id);
        return Result.success(course);
    }
}
