package org.areo.zhihui.pojo.entity;

import lombok.Data;

@Data
public class Teacher extends User {
    private Integer userId;
    private String information;
}
