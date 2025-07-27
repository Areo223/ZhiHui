package org.areo.zhihui.pojo.entity;

import lombok.Data;
// 课程实例
@Data
public class TeachingClass extends BaseEntity{
    private Integer id;
    private Integer courseId;
    private Integer maxCapacity;
    private Integer teacherId;
    // 当前容量
    private Integer currentCapacity;
    //教学班编号
    private String teachingClassCode;
}
