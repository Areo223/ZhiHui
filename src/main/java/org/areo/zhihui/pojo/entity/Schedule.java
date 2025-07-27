package org.areo.zhihui.pojo.entity;

import lombok.Data;

@Data
// 排课结果表
// 用于存储排课结果，包括教学班id,周几,时间片id、排课教室、单双周安排等信息
public class Schedule extends BaseEntity{
    private Integer id;
    private Integer teachingClassId;
    private Integer dayOfWeek;
    private Integer timeSlotId;
    private Integer classroomId;
    private Integer singleDoubleWeek;
}
