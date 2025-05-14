package org.areo.zhihui.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class AddCourseRequest{
    @NotBlank(message = "课程名称不能为空")
    @Size(max = 100, message = "课程名称长度不能超过100")
    protected String courseName;

    @NotBlank(message = "课程代码不能为空")
    @Size(max = 20, message = "课程代码长度不能超过20")
    protected String courseCode;

    @Nullable
    @Size(max = 50, message = "课程类型长度不能超过50")
    private String courseType;

    @Nullable
    @Size(max = 200, message = "课程描述长度不能超过200")
    private String courseDesc;
    
    @Nullable
    private Integer teacherId;
}