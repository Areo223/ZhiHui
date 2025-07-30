package org.areo.zhihui.services;


import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Timeslot;
import org.areo.zhihui.pojo.vo.TimeSlotVO;

import java.util.List;

public interface TimeslotService {
    Result<List<TimeSlotVO>> getAllTimeslot();

    Result<TimeSlotVO> addTimeslot(Timeslot timeslot);

    Result<Void> deleteTimeslot(List<Integer> ids);

    Result<TimeSlotVO> updateTimeslot(Timeslot timeslot);

    Result<List<TimeSlotVO>> getTimeslotByIds(List<Integer> ids);
}
