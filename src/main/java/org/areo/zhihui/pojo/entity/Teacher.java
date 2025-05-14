package org.areo.zhihui.pojo.entity;

import lombok.Data;

@Data
public class Teacher extends User {
    //对应用户id
    private Integer userId;
    //对应学院id
    private Integer collegeId;
    //职称
    private String title;
    //个人信息
    private String information;
}
