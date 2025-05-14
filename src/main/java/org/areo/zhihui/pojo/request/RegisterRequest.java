package org.areo.zhihui.pojo.request;

import lombok.Data;
import org.areo.zhihui.utils.enums.RoleEnum;

@Data
public class RegisterRequest {
    private String identifier;
    private String password;
    private String name;
    private RoleEnum role;
}
