package org.areo.zhihui.pojo.request;

import lombok.Data;

@Data
public class UserAllRequest extends UserBaseRequest{
    private String name;
    private String identifier;
    private String password;
    private String role;
}
