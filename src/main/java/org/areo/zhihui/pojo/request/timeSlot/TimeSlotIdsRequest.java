package org.areo.zhihui.pojo.request.timeSlot;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TimeSlotIdsRequest {
    @NotEmpty
    @NotNull
    @Schema(description = "时间槽id列表")
    private List<Integer> ids;
}
