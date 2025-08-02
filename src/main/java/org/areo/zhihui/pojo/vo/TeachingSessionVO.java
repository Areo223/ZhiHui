package org.areo.zhihui.pojo.vo;

import lombok.Data;

@Data
public class TeachingSessionVO {
    private Integer id;
    // 课程实例id
    private Integer offeringId;
    // 教师id
    private Integer teacherId;
    // 教室
    private ClassroomVO classroom;
    // 时间片
    private TimeslotVO timeslot;
}
