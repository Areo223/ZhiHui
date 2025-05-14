package org.areo.zhihui.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Grade extends BaseEntity {
    private Integer id;
    private Integer gradeCode;
    private LocalDateTime enrollmentYear;
}
