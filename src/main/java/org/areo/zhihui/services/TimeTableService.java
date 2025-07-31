package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.dto.TimeTable;

public interface TimeTableService {
    Result<TimeTable> testSolve(TimeTable problem);

    Result<Void> solve(TimeTable problem);

    Result<TimeTable> getTimeTable();

    Result<Void> stopSolving();
}
