package org.areo.zhihui.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TeacherUpdOwnRequest  {
    //个人信息
    @Schema(description = "其他信息")
    @NotNull
    private String information;
}
