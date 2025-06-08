package org.areo.zhihui.utils.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


@Getter
public enum CourseTypeEnum implements IEnum<Integer>{
    NORMAL(0,"普通课程"),
    LAB(1,"实验课程"),
    PE(2,"体育课程"),
    ENGLISH(3,"英语课程"),
    ELECTIVE(4,"选修课程"),
    COMPULSORY(5,"必修课程"),
    MINOR(6,"辅修课程");

    private final Integer value;

    @JsonValue
    private final String desc;

    CourseTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }

}
