package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.pojo.vo.CourseOfferingVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnrollmentService {
    Result<Void> selectCourse(Enrollment enrollment);

    Result<Void> withdrawCourse(Enrollment enrollment);

    Result<List<CourseOfferingVO>> getSelectedCourse();
}
