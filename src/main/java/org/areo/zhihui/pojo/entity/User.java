package org.areo.zhihui.pojo.entity;


import lombok.Data;
import org.areo.zhihui.utils.enums.LockedEnum;
import org.areo.zhihui.utils.enums.RoleEnum;

@Data
public class User extends BaseEntity {
    //用户id
    private Integer id;
//    @TableField(fill = FieldFill.INSERT)
//    private Integer num;
    //姓名
    private String name;
    //密码
    private String password;
    //学号或工号
    private String identifier;
    //角色
    private RoleEnum role;
    //是否锁定
    private LockedEnum locked;

    public boolean checkIfLocked() {
        return locked.getValue() == 1;
    }
}
