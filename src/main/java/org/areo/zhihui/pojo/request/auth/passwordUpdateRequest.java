package org.areo.zhihui.pojo.request.auth;

import lombok.Data;

@Data
public class passwordUpdateRequest {
    private String oldPassword;
    private String newPassword;
}
