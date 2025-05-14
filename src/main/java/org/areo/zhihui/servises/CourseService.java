package org.areo.zhihui.servises;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    Result<Void> addCourse(Course course);

    Result<Void> deleteCourse(Integer id);

    Result<List<Course>> getCourse();
}
