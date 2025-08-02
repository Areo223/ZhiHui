package org.areo.zhihui.pojo.entity;

import lombok.Data;

@Data
public class Enrollment extends BaseEntity{
    //对应学生identifier
    private String studentIdentifier;
    //对应课程编号
    private Integer courseOfferingId;
    //成绩
    private Integer grade;

}
