package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.EnrollmentMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.services.CourseSelectionService;
import org.areo.zhihui.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentMapper enrollmentMapper;
    private final CourseSelectionService courseSelectionService;

    @Override
    public Result<Void> selectCourse(Enrollment enrollment) {
        try {
            courseSelectionService.selectCourse(enrollment.getStudentIdentifier(), enrollment.getTeachingClassCode());
        } catch (Exception e) {
            return Result.failure(e);
        }

        return Result.success(null);
    }
}

