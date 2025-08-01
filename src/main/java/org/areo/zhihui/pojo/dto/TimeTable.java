package org.areo.zhihui.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.areo.zhihui.pojo.entity.BaseEntity;
import org.areo.zhihui.pojo.entity.Classroom;
import org.areo.zhihui.pojo.entity.TeachingSession;
import org.areo.zhihui.pojo.entity.Timeslot;
import org.areo.zhihui.utils.HardSoftScoreTypeHandler;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@PlanningSolution
@AllArgsConstructor
@NoArgsConstructor
@TableName(autoResultMap = true)
public class TimeTable extends BaseEntity {

//    @TableId(type = IdType.ASSIGN_UUID)
    private String id = "1";
    private SolverStatus solverStatus;
    @PlanningScore
    @TableField(typeHandler = HardSoftScoreTypeHandler.class)
    private HardSoftScore score;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    @TableField(exist = false)
    private List<Timeslot> timeslots = new ArrayList<>();
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    @TableField(exist = false)
    private List<Classroom> classrooms = new ArrayList<>();
    @PlanningEntityCollectionProperty
    @TableField(exist = false)
    private List<TeachingSession> sessions = new ArrayList<>();

}
