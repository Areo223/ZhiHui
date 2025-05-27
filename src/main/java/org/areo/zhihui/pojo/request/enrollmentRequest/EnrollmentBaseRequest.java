package org.areo.zhihui.pojo.request.enrollmentRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.Course;

@Data
public class EnrollmentBaseRequest {
    @Exists(entity = Course.class, message = "课程不存在")
    @NotNull(message = "课程ID不能为空")
    private Integer courseId;
}
