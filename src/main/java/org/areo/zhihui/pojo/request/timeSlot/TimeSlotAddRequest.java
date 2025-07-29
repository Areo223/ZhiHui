package org.areo.zhihui.pojo.request.timeSlot;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeSlotAddRequest {
    @NotNull
    @Schema(description = "时间槽名称")
    private String name;
    @NotNull
    @Schema(description = "时间槽开始时间")
    private LocalTime startTime;
    @NotNull
    @Schema(description = "时间槽结束时间")
    private LocalTime endTime;
}
