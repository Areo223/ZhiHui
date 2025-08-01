package org.areo.zhihui.services;


import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Timeslot;
import org.areo.zhihui.pojo.vo.TimeslotVO;

import java.util.List;

public interface TimeslotService {
    Result<List<TimeslotVO>> getAllTimeslot();

    Result<TimeslotVO> addTimeslot(Timeslot timeslot);

    Result<Void> deleteTimeslot(List<Integer> ids);

    Result<TimeslotVO> updateTimeslot(Timeslot timeslot);

    Result<List<TimeslotVO>> getTimeslotByIds(List<Integer> ids);
}
