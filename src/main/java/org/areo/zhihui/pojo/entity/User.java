package org.areo.zhihui.pojo.entity;


import lombok.Data;
import org.areo.zhihui.utils.enums.LockedEnum;
import org.areo.zhihui.utils.enums.RoleEnum;

@Data
public class User extends BaseEntity {
    private Integer id;
//    @TableField(fill = FieldFill.INSERT)
//    private Integer num;
    private String name;
    private String password;
    private String identifier;
    private RoleEnum role;
    private LockedEnum locked;

    public boolean checkIfLocked() {
        return locked.getValue() == 1;
    }
}
