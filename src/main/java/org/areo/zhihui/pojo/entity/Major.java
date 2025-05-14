package org.areo.zhihui.pojo.entity;

import lombok.Data;

@Data
public class Major extends BaseEntity{
    //专业id
    private Integer id;
    //专业名称
    private String name;
    //对应学院id
    private Integer collegeId;
    //专业负责人
    private Integer principal;

}
