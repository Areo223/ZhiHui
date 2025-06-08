package org.areo.zhihui.pojo.request.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(description = "学号或公号", example = "2023211005")
    private String identifier;

    @Schema(description = "密码", example = "Ab12345678")
    private String password;
}
