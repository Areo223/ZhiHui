package org.areo.zhihui.pojo.request;

import lombok.Data;
import org.areo.zhihui.utils.enums.SemesterEnum;

@Data
public class CourseOfferingAddRequest {
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
}
