package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student")
public class Student extends User {

    private Integer userId;
    private String information;
}
