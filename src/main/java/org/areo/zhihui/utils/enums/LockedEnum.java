package org.areo.zhihui.utils.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LockedEnum implements IEnum<Integer> {
    LOCKED(1,"已锁定"),
    NOT_LOCKED(0,"未锁定");

    private final Integer value;
    @JsonValue
    private final String desc;

    @Override
    public Integer getValue() {
        return this.value;
    }

}
