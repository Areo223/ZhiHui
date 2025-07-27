package org.areo.zhihui.pojo.vo;

import lombok.Getter;

@Getter
public class TeachingClassVO {
    private Integer id;
    private Integer courseId;
    private Integer maxCapacity;
    private Integer teacherId;
    // 当前容量
    private Integer currentCapacity;
    //教学班编号
    private String teachingClassCode;
}
