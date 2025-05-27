package org.areo.zhihui.utils.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

@Getter
public enum SemesterEnum implements IEnum<Integer> {
    SPRING(1,"春季学期"),
    SUMMER(2,"夏季学期"),
    AUTUMN(3,"秋季学期");

    private final Integer value;
    @JsonValue
    private final String desc;

    SemesterEnum(Integer code, String desc) {
        this.value = code;
        this.desc = desc;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }

    // 根据月份自动判断学期
    public static SemesterEnum fromMonth(Month month) {
        // 春季学期为2到6月
       if (Arrays.asList(Month.FEBRUARY,Month.MARCH, Month.APRIL, Month.MAY,Month.JUNE).contains(month)) {
            return SPRING;
       }
       // 秋季学期为9到1月
       else if (Arrays.asList(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER,Month.DECEMBER,Month.JANUARY).contains(month)) {
            return AUTUMN;
       }
       // 夏季学期为7月
       else if (Arrays.asList(Month.JULY).contains(month))  {
            return SUMMER;
       }
       // 其他月份返回null
       else {
           return null;
       }
    }

    // 根据日期自动判断学期
    public static SemesterEnum semesterSet() {
        return fromMonth(LocalDateTime.now().getMonth());
    }
}
