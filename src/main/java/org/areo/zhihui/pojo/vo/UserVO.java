package org.areo.zhihui.pojo.vo;

import lombok.Data;
import org.areo.zhihui.utils.enums.LockedEnum;
import org.areo.zhihui.utils.enums.RoleEnum;

@Data
public class UserVO {
    private Integer id;
    private String name;
    private String identifier;
    private RoleEnum role;
}
