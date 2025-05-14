package org.areo.zhihui.utils.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum DeletedEnum implements IEnum<Integer> {
    DELETED(1,"已删除"),
    NOT_DELETED(0,"未删除");


    private final Integer value;
    @JsonValue
    private final String desc;

    DeletedEnum(int code, String desc) {
        this.value = code;
        this.desc = desc;

    }

    @Override
    public Integer getValue() {
        return this.value;
    }




}

