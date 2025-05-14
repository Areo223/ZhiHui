package org.areo.zhihui.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.areo.zhihui.utils.enums.RoleEnum;

@Data
public class RegisterRequest {
    @Schema(description = "学号或公号", example = "2023211005")
    @NotBlank(message = "学号或公号不能为空")
    private String identifier;

    @Schema(description = "密码", example = "Ab12345678")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "姓名", example = "张三")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Schema(description = "角色", example = "学生")
    @NotBlank(message = "角色不能为空")
    private RoleEnum role;
}
