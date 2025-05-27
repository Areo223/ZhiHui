package org.areo.zhihui.pojo.entity;

import lombok.Data;
import org.areo.zhihui.utils.enums.SemesterEnum;

@Data
public class Enrollment extends BaseEntity{
    //对应学生id
    private Integer studentId;
    //对应课程id
    private Integer courseId;
    //成绩
    private String grade;
    //学期
    private SemesterEnum semester;

}
