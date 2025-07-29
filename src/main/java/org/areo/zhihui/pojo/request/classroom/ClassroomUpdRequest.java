package org.areo.zhihui.pojo.request.classroom;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassroomUpdRequest extends ClassroomAddRequest {
    //教室id
    @NotNull
    @Schema(description = "教室id",example = "1")
    private Integer id;
}
