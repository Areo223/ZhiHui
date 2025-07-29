package org.areo.zhihui.services;


import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.TimeSlot;
import org.areo.zhihui.pojo.vo.TimeSlotVO;

import java.util.List;

public interface TimeSlotService {
    Result<List<TimeSlotVO>> getAllTimeSlot();

    Result<TimeSlotVO> addTimeSlot(TimeSlot timeSlot);

    Result<Void> deleteTimeSlot(List<Integer> ids);

    Result<TimeSlotVO> updateTimeSlot(TimeSlot timeSlot);

    Result<List<TimeSlotVO>> getTimeSlotByIds(List<Integer> ids);
}
