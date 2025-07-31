package org.areo.zhihui.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.areo.zhihui.pojo.entity.Classroom;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.pojo.entity.TeachingSession;
import org.areo.zhihui.pojo.entity.Timeslot;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

@Data
@PlanningSolution
@AllArgsConstructor
@NoArgsConstructor
public class TimeTable {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    @TableField(exist = false)
    private List<Timeslot> timeslots;
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    @TableField(exist = false)
    private List<Classroom> classrooms;
    @PlanningEntityCollectionProperty
    @TableField(exist = false)
    private List<TeachingSession> sessions;

    @PlanningScore
    private HardSoftScore score;

    private SolverStatus solverStatus;

    // 添加便捷构造方法
    public TimeTable(List<Timeslot> timeslots,
                     List<TeachingSession> sessions) {
        this.timeslots = timeslots;
        this.sessions = sessions;
    }
}
