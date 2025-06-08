package org.areo.zhihui.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import org.areo.zhihui.utils.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class UserListRequest {

    @Nullable
    @Schema(description = "页码")
    private Integer pageNum = 1;
    @Nullable
    @Schema(description = "每页大小")
    private Integer pageSize = 10;

    @Getter
    @Nullable
    @Schema(description = "排序字段", example = "{\"name\":true,\"age\":false}")
    private Map<String,Boolean> sorts;

    @Nullable
    @Schema(description = "查询条件")
    private Conditions conditions;

    @Data
    public class Conditions{
        @Nullable
        @Schema(description = "用户名称")
        private String name;
        @Nullable
        @Schema(description = "学号或工号")
        private String identifier;
        @Nullable
        @Schema(description = "角色")
        private RoleEnum role;
        @Nullable
        @Schema(description = "是否锁定")
        private String locked;
        @Nullable
        @Schema(description = "创建起始时间")
        private LocalDateTime startTime;
        @Nullable
        @Schema(description = "创建结束时间")
        private LocalDateTime endTime;
    }
}
