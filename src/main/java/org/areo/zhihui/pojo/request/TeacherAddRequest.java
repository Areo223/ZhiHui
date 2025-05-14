package org.areo.zhihui.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.College;
import org.areo.zhihui.pojo.entity.User;
import org.springframework.lang.Nullable;

@Data
public class TeacherAddRequest {
    //对应用户id
    @Schema(description = "用户id")
    @Exists(entity = User.class,message = "用户不存在")
    @NotNull(message = "用户id不能为空")
    private Integer userId;

    //学院id
    @Schema(description = "学院id")
    @Exists(entity = College.class,message = "学院不存在")
    @NotNull(message = "学院id不能为空")
    private Integer collegeId;

    //职称
    @Schema(description = "职称")
    @Nullable
    private String title;

    //个人信息
    @Schema(description = "其他信息")
    @Nullable
    private String information;

}
