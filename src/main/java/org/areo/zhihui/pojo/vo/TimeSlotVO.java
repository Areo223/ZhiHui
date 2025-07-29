package org.areo.zhihui.pojo.vo;

import org.areo.zhihui.pojo.entity.TimeSlot;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TimeSlotVO{
    private Integer id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
}
