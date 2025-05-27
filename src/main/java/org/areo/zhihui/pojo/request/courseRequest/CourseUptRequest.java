package org.areo.zhihui.pojo.request.courseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseUptRequest extends CourseAddRequest {
    @Schema(description = "课程ID", example = "1")
    @NotNull(message = "课程ID不能为空")
    private Integer id;
}
