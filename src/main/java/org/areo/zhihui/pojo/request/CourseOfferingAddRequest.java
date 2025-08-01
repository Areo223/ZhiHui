package org.areo.zhihui.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.areo.zhihui.utils.enums.SemesterEnum;

@Data
@Schema(description = "课程实例添加请求体")
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
    //课程容量,最小为0,最大500
    @Schema(name = "课程容量",minimum = "0",maximum = "500",example = "30")
    @Min(value = 0,message = "课程容量不能小于0")
    @Max(value = 500,message = "课程容量不能大于500")
    private Integer maxCapacity;
}
