package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.areo.zhihui.utils.enums.CourseStatusEnum;
import org.areo.zhihui.utils.enums.CourseTypeEnum;

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
    private CourseTypeEnum courseType;
    //课程描述
    private String courseDesc;
    //课程总学时
    private Integer totalHours;
    //课程总周数
    private Integer weeklyHours;

    //学分
    private Integer credit;
    //考试时间
    private String examTime;
    //课程状态
    private CourseStatusEnum status;

}
