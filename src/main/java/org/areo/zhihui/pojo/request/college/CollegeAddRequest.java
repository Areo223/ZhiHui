package org.areo.zhihui.pojo.request.college;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollegeAddRequest {

    // 学院名称
    @NotNull
    private String collegeName;
    //学院负责人id
    @Nullable
    private Integer collegeHeadId;
}
