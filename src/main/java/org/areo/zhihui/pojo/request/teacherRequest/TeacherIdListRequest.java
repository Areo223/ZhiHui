package org.areo.zhihui.pojo.request.teacherRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.Teacher;

import java.util.List;

@Data
public class TeacherIdListRequest {
    @Schema(description = "教师id列表")
    @Exists(entity = Teacher.class, message = "教师不存在")
    @NotNull
    private List<Integer> ids;

}
