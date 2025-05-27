package org.areo.zhihui.pojo.request.teacherRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.Teacher;

import java.util.List;

@Data
public class TeacherBaseRequest {
    @Exists(entity = Teacher.class, message = "教师不存在")
    @NotNull
    private List<Integer> ids;

}
