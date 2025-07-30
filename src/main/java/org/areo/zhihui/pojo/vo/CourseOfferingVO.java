package org.areo.zhihui.pojo.vo;

import lombok.Data;
import org.areo.zhihui.utils.enums.SemesterEnum;

@Data
public class CourseOfferingVO {
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
    private Integer currentCapacity;
}
