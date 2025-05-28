package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Teacher extends User {
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //对应用户id,不需要自动生成
    private Integer userId;
    //对应学院id
    private Integer collegeId;
    //职称
    private String title;
    //个人信息
    private String information;
}
