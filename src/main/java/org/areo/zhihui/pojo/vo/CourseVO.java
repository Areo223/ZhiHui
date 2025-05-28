package org.areo.zhihui.pojo.vo;

import lombok.Data;
import org.areo.zhihui.utils.enums.CourseStatusEnum;

@Data
public class CourseVO {
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
    //课程容量
    private Integer capacity;
    //课程总学时
    private Integer totalHours;
    //考试时间
    private String examTime;
    //课程状态
    private CourseStatusEnum status;
}
