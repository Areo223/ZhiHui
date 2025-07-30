package org.areo.zhihui.pojo.vo;

import lombok.Getter;

@Getter
public class TeachingSessionVO {
    private Integer id;
    // 课程实例id
    private Integer offeringId;
    // 时间片id
    private Integer timeslotId;
    // 教室id
    private Integer classroomId;
}
