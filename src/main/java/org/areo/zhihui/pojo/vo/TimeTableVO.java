package org.areo.zhihui.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.areo.zhihui.pojo.entity.Classroom;
import org.areo.zhihui.pojo.entity.TeachingSession;
import org.areo.zhihui.pojo.entity.Timeslot;
import org.areo.zhihui.utils.HardSoftScoreTypeHandler;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
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
