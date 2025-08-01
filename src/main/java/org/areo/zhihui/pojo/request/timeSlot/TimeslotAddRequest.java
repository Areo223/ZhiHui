package org.areo.zhihui.pojo.request.timeSlot;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class TimeslotAddRequest {
    @NotNull
    @Schema(description = "时间槽名称",minLength = 1,maxLength = 10,example = "1-2")
    private String name;
    @NotNull
    @Schema(description = "时间槽开始时间",example = "09:00")
    private LocalTime startTime;
    @NotNull
    @Schema(description = "时间槽结束时间",example = "10:00")
    private LocalTime endTime;
    @NotNull
    @Schema(description = "时间槽星期几",example = "MONDAY")
    private DayOfWeek dayOfWeek;
}
