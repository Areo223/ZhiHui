package org.areo.zhihui.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
// 时间片表
// 用于存储时间段信息，包括开始时间和结束时间
public class Timeslot{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @Schema(description = "时间片名称",minLength = 1,maxLength = 10,example = "1-2")
    private String name;
    private DayOfWeek dayOfWeek;
    @Schema(description = "开始时间",example = "20:00:00")
    private LocalTime startTime;
    @Schema(description = "结束时间",example = "21:00:00")
    private LocalTime endTime;
}
