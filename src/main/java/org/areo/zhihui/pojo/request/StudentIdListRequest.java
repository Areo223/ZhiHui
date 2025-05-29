package org.areo.zhihui.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.Student;

@Data
public class StudentIdListRequest {
    @Schema(description = "学生id列表")
    @NotNull
    @Exists(entity = Student.class, message = "学生不存在")
    private Integer[] ids;
}
