package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student")
public class Student extends User {
    //对应学生id
    private Integer userId;
    //对应班级id
    private Integer classId;
    //学分绩
    private Integer creditScore;
    //个人信息
    private String information;
}
