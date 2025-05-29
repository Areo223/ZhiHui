package org.areo.zhihui.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentUpdOwnRequest {
    @NotNull(message = "修改内容不能为空")
    private String information;
}
