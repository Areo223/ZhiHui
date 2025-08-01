package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.dto.TimeTable;
import org.areo.zhihui.pojo.vo.TimeTableVO;
import org.optaplanner.core.api.solver.SolverStatus;

public interface TimeTableService {
    Result<TimeTable> testSolve(TimeTable problem);

    Result<Void> solve();

    Result<TimeTableVO> getTimeTable();

    Result<Void> stopSolving();

    Result<SolverStatus> getStatus();

    Result<TimeTable> saveSolution(TimeTable timeTable);
}
