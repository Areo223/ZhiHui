package org.areo.zhihui.pojo.vo;

import lombok.Data;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class TimeTableVO {
    private SolverStatus solverStatus;
    private HardSoftScore score;
    private List<TeachingSessionVO> sessionVOList = new ArrayList<>();
}
