package org.areo.zhihui.pojo.request.college;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.College;

import java.util.List;

@Data
public class CollegeIdsRequest {
    @Exists(entity = College.class,message = "学院不存在")
    @Schema(description = "学院id列表",example = "[1, 2, 3]")
    @NotNull
    @NotEmpty
    private List<Integer> collegeIdList;
}
