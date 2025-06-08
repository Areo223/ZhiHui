package org.areo.zhihui.utils.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


@Getter
public enum RoomTypeEnum implements IEnum<Integer> {
    NORMAL(0,"普通教室"),
    LAB(1,"实验教室"),
    PE(2,"体育教室");
    private final Integer value;
    @JsonValue
    private final String desc;
    RoomTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
