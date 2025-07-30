package org.areo.zhihui.pojo.entity;

import lombok.Data;
import org.areo.zhihui.utils.enums.SemesterEnum;

@Data
public class Enrollment extends BaseEntity{
    //对应学生identifier
    private String studentIdentifier;
    //对应课程编号
    private String courseOfferingId;
    //成绩
    private String grade;
    //学期
    private SemesterEnum semester;

}
