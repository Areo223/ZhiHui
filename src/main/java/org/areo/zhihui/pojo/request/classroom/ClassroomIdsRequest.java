package org.areo.zhihui.pojo.request.classroom;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ClassroomIdsRequest {
    @NotNull
    @NotEmpty
    @Schema(description = "教室ID列表",example = "[1,2,3]")
    private List<Integer> classroomIds;
}
