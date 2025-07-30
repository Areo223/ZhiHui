package org.areo.zhihui.pojo.request.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.areo.zhihui.utils.enums.CourseStatusEnum;
import org.areo.zhihui.utils.enums.CourseTypeEnum;
import org.springframework.lang.Nullable;

@Data
public class CourseAddRequest {
    @NotBlank(message = "课程名称不能为空")
    @Size(max = 100, message = "课程名称长度不能超过100")
    @Schema(description = "课程名称", example = "Java 编程")
    protected String courseName;

    @NotBlank(message = "课程代码不能为空")
    @Size(max = 20, message = "课程代码长度不能超过20")
    @Schema(description = "课程代码", example = "CS101")
    protected String courseCode;

    @Nullable
    @Size(max = 50, message = "课程类型长度不能超过50")
    @Schema(description = "课程类型", example = "普通课程")
    private CourseTypeEnum courseType;

    @Nullable
    @Size(max = 200, message = "课程描述长度不能超过200")
    @Schema(description = "课程描述", example = "这是一门介绍Java编程的课程")
    private String courseDesc;

    // 课程总学时
    private Integer totalHours;
    // 课程总周数
    private Integer weeklyHours;
    // 学分
    private Integer credit;
    // 考试时间
    private String examTime;
    // 课程状态
    private CourseStatusEnum status;
}