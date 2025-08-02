package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Course;
import org.areo.zhihui.pojo.vo.CourseOfferingVO;
import org.areo.zhihui.pojo.vo.CourseVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    Result<Void> addCourse(Course course);

    Result<Void> deleteCourse(Integer id);

    Result<List<CourseVO>> getCourse();

    Result<Void> updateCourse(Course course);

    Result<List<CourseOfferingVO>> getSelectedCourse();
}
