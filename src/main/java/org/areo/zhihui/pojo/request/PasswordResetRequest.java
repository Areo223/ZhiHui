package org.areo.zhihui.pojo.request;

import lombok.Data;

@Data
public class PasswordResetRequest {
//    //验证码
//    private String code;
    //新密码
    private String newPassword;
}
