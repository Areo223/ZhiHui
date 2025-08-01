package org.areo.zhihui.pojo.request.timeSlot;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TimeslotUpdateRequest extends TimeslotAddRequest{
    @NotNull(message = "时间槽id不能为空")
    @Schema(description = "时间槽id",example = "1")
    private Integer id;

}
