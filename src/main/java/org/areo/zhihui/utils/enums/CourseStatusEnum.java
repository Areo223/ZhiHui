package org.areo.zhihui.utils.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CourseStatusEnum implements IEnum<Integer> {
    NOT_STARTED(0, "未开始"),
    IN_PROGRESS(1, "进行中"),
    COMPLETED(-1, "已完成");
    private final Integer value;
    @JsonValue
    private final String desc;
    CourseStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    @Override
    public Integer getValue() {
        return this.value;
    }
}
