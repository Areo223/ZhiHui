package org.areo.zhihui.pojo.request.college;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollegeUpdRequest extends CollegeAddRequest {
    // 学院id
    @NotNull
    @Schema(description = "学院id")
    private Integer collegeId;
}
