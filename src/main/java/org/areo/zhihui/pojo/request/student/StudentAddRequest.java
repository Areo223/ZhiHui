package org.areo.zhihui.pojo.request.student;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.Class;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.lang.Nullable;

@Data
public class StudentAddRequest {
    private final RoleEnum role = RoleEnum.STUDENT;

    @Schema(description = "用户id")
    @Exists(entity = User.class,message = "用户不存在", roles = RoleEnum.STUDENT)
    private Integer userId;

    @Schema(description = "班级id")
    @Exists(entity = Class.class,message = "班级不存在")
    private Integer classId;

    @Schema(description = "其他信息")
    @Nullable
    private String information;

}
