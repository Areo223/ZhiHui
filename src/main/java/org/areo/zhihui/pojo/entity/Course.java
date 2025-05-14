package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class Course extends BaseEntity {
    private Integer id;
    private String courseName;
    private String courseCode;
    private String courseType;
    private String courseDesc;
    private Integer teacherId;
    @TableField(exist = false)
    private List<Student> students;

}
