package org.areo.zhihui.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
// 时间片表
// 用于存储时间段信息，包括开始时间和结束时间
public class Timeslot{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
