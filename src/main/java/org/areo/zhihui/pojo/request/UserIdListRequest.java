package org.areo.zhihui.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.User;

import java.util.List;

@Data
public class UserIdListRequest {
    @Exists(entity = User.class, message = "id对应用户不存在")
    @NotNull(message = "id列表不能为空")
    private List<Integer> ids;
}
