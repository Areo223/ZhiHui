package org.areo.zhihui.pojo.request.enrollment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentBaseRequest {
    @NotNull(message = "课程编号不能为空")
    private String Teaching_Class_Code;
}
