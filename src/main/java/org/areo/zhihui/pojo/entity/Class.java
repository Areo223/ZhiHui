package org.areo.zhihui.pojo.entity;

import lombok.Data;

@Data
public class Class extends BaseEntity{
    //班级编号
    private Integer id;
    //对应专业id
    private Integer majorId;
    //对应年级id
    private Integer gradeId;
    //班主任id
    private Integer headteacherId;


}
