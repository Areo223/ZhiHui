package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.areo.zhihui.mapper.ClassroomMapper;
import org.areo.zhihui.mapper.TeacherUnavailableMapper;
import org.areo.zhihui.mapper.TimeslotMapper;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.ArrayList;
import java.util.List;

// 教学班实体,专注于课程实例的时间和教室的分配
@PlanningEntity
@Data
public class TeachingSession {
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 课程实例id
    private Integer offeringId;
    // 时间片id
    private Integer timeslotId;
    // 教室id
    private Integer classroomId;
    // 教师id
    private Integer teacherId;
    // 授课人数
    private Integer studentCount;

    // 时间片,规划时的对象
    @PlanningVariable
    @TableField(exist = false)
    private Timeslot timeslot;
    // 授课教室,规划时的对象
    @PlanningVariable
    @TableField(exist = false)
    private Classroom classroom;
    // 教师不可用时间
    @TableField(exist = false)
    private List<Timeslot> timeslots = new ArrayList<>();
    // 从数据库加载后初始化对象
    public void initPlanningVariables(TimeslotMapper tsMapper, ClassroomMapper crMapper, TeacherUnavailableMapper tuMapper) {
        if (timeslotId != null) {
            this.timeslot = tsMapper.selectById(timeslotId);
        }
        if (classroomId != null) {
            this.classroom = crMapper.selectById(classroomId);
        }
        if (teacherId != null) {
            if (tuMapper.selectById(teacherId) != null) {
                this.timeslots = tsMapper.selectByIds(tuMapper.selectById(teacherId).getTimeslotIds());
            }
        }
    }
}
