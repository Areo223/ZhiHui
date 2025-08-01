package org.areo.zhihui.pojo.request.classroom;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.areo.zhihui.utils.enums.RoomTypeEnum;

@Data
public class ClassroomAddRequest {
    //教室名称
    @NotNull
    @Schema(description = "教室名称,不可为空",minLength = 1,maxLength = 10,example = "H101")
    private String roomName;
    //教室容量
    @NotNull
    @Schema(description = "教室容量,不可为空",minimum = "0",maximum = "1000",example = "30")
    private Integer capacity;
    //教室类型
    @Nullable
    @Schema(description = "教室类型,可为空",example = "普通教室")
    private RoomTypeEnum roomType;
}
