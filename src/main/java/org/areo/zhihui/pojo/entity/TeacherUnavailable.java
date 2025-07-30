package org.areo.zhihui.pojo.entity;

import lombok.Data;

@Data
// 教师不可排课表
// 用于存储教师不可排课信息，包括教师id,时间片id等信息
public class TeacherUnavailable extends BaseEntity{
    private Integer teacherId;
    private Integer timeSlotId;
}
