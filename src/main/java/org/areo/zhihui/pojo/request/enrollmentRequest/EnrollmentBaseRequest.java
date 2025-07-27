package org.areo.zhihui.pojo.request.enrollmentRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.Course;

@Data
public class EnrollmentBaseRequest {
    @NotNull(message = "课程编号不能为空")
    private String Teaching_Class_Code;
}
