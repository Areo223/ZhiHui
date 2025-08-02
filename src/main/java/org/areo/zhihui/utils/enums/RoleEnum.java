package org.areo.zhihui.utils.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RoleEnum implements IEnum<Integer> {
    STUDENT(0,"学生"),
    TEACHER(1,"教师"),
    ADMIN(2,"管理员");

    private final Integer code;
    @JsonValue
    private final String desc;

    RoleEnum(int code, String name){
        this.code = code;
        this.desc = name;
    };

    public static RoleEnum getEnumByDesc(String desc) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.desc.equals(desc)) {
                return roleEnum;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }
}
