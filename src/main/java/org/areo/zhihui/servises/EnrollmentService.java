package org.areo.zhihui.servises;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.springframework.stereotype.Service;

@Service
public interface EnrollmentService {
    Result<Void> selectCourse(Enrollment enrollment);
}
