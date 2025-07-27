package org.areo.zhihui.pojo.entity;

import lombok.Data;

@Data
// 时间片表
// 用于存储时间段信息，包括开始时间和结束时间
public class TimeSlot extends BaseEntity{
    private Integer id;
    private String name
            ;
    private String startTime;
    private String endTime;
}
