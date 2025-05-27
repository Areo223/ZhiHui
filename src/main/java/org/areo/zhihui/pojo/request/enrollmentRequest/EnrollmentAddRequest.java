package org.areo.zhihui.pojo.request.enrollmentRequest;

import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.Student;


public class EnrollmentAddRequest extends EnrollmentBaseRequest {
    @Exists(entity = Student.class, message = "学生不存在")
    private Integer studentId;
}
