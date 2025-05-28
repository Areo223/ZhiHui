package org.areo.zhihui.pojo.vo;

import lombok.Data;

@Data
public class TeacherVO extends UserVO{

    //对应学院id
    private Integer collegeId;
    //职称
    private String title;
    //个人信息
    private String information;
}
