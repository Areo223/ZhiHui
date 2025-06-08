package org.areo.zhihui.pojo.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserBaseRequest {
    @Schema(description = "用户id", example = "1")
    private Integer id;
}
