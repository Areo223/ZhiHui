package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.areo.zhihui.utils.enums.CourseStatusEnum;

import java.util.List;

@Data
public class Course extends BaseEntity {
    //课程id
    private Integer id;
    //课程名称
    private String courseName;
    //课程代码
    private String courseCode;
    //课程类型
    private String courseType;
    //学分
    private Integer credit;
    //课程描述
    private String courseDesc;
    //教师id
    private Integer teacherId;
    //面向专业
    private Integer majorId;
    //面向年级
    private Integer gradeId;
    //课程容量
    private Integer capacity;
    //课程总学时
    private Integer totalHours;
    //考试时间
    private String examTime;
    //课程状态
    private CourseStatusEnum status;

}
