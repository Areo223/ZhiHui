package org.areo.zhihui.pojo.request.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserUpdRequest extends UserBaseRequest {
    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "学号或公号", example = "2023211005")
    private String identifier;

    @Schema(description = "密码", example = "Ab12345678")
    private String password;

    @Schema(description = "角色", example = "学生")
    private String role;
}
