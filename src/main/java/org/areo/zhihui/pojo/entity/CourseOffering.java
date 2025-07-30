package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.areo.zhihui.utils.enums.SemesterEnum;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseOffering {
    //课程实例id
    private Integer id;
    //课程id
    private Integer courseId;
    //授课教师
    private Integer teacherId;
    //面向专业
    private String targetMajors;
    //面向年级
    private String targetGrades;
    //面向学期
    private SemesterEnum targetSemester;
    //课程容量
    private Integer maxCapacity;
    //当前容量,创建时默认为最大容量
    @TableField(fill = FieldFill.INSERT)
    private Integer currentCapacity;


    @TableField(exist = false)
    private List<TeachingSession> teachingSessions = new ArrayList<>();
    @TableField(exist = false)
    private Teacher teacher;
    @TableField(exist = false)
    private Course course;

}
