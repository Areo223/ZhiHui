package org.areo.zhihui.pojo.vo;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class TimeslotVO {
    private Integer id;
    private String name;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
