package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import org.areo.zhihui.mapper.ClassroomMapper;
import org.areo.zhihui.mapper.TeachingSessionMapper;
import org.areo.zhihui.mapper.TimeTableMapper;
import org.areo.zhihui.mapper.TimeslotMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.dto.TimeTable;
import org.areo.zhihui.pojo.entity.Classroom;
import org.areo.zhihui.pojo.entity.TeachingSession;
import org.areo.zhihui.pojo.entity.Timeslot;
import org.areo.zhihui.pojo.vo.ClassroomVO;
import org.areo.zhihui.pojo.vo.TeachingSessionVO;
import org.areo.zhihui.pojo.vo.TimeTableVO;
import org.areo.zhihui.pojo.vo.TimeslotVO;
import org.areo.zhihui.services.TimeTableService;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.areo.zhihui.mapper.TimeTableMapper.SINGLETON_TIME_TABLE_ID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TimeTableServiceImpl implements TimeTableService {
    // 使用明确的常量定义
    public static final UUID SINGLETON_SOLVING_ID = UUID.randomUUID();
    private final TimeTableMapper timeTableMapper;
    private final SolverManager<TimeTable, UUID> solverManager;
    private final TimeslotMapper timeslotMapper;
    private final ClassroomMapper classroomMapper;
    private final TeachingSessionMapper teachingSessionMapper;

    @Override
    public Result<TimeTable> testSolve(TimeTable problem) {
        //检查problem是否存在session,timeslot,classroom
        if (!problem.getSessions().isEmpty() && !problem.getTimeslots().isEmpty() && !problem.getClassrooms().isEmpty()) {
            timeTableMapper.insert(problem); // 保存传入的problem
            UUID problemId = UUID.randomUUID();

            SolverJob<TimeTable, UUID> solverJob = solverManager.solve(problemId, problem);
            TimeTable solution;
            try {
                solution = solverJob.getFinalBestSolution();

            } catch (InterruptedException | ExecutionException e) {
                return Result.failure(e);
            }
            return Result.success(solution);
        } else {
            //如果不传入problem,则自动构建baseProblem
            TimeTable baseProblem = new TimeTable();
            baseProblem.setTimeslots(timeslotMapper.selectList(null));
            baseProblem.setClassrooms(classroomMapper.selectList(null));
            baseProblem.setSessions(teachingSessionMapper.selectList(null));
            UUID problemId = UUID.randomUUID();

            SolverJob<TimeTable, UUID> solverJob = solverManager.solve(problemId, baseProblem);

            TimeTable solution;
            try {
                solution = solverJob.getFinalBestSolution();

            } catch (InterruptedException | ExecutionException e) {
                return Result.failure(e);
            }
            // 保存解决方案
            saveSolution(solution);
            return Result.success(solution);
        }

    }


    @Override
    public Result<Void> solve() {
        //自动构建baseProblem
        TimeTable baseProblem = new TimeTable();
        baseProblem.setTimeslots(timeslotMapper.selectList(null));
        baseProblem.setClassrooms(classroomMapper.selectList(null));
        baseProblem.setSessions(teachingSessionMapper.selectList(null));

        // 异步求解,方法立即返回
        // 每当找到更好的解,都会调用timeTableMapper::updateByIds方法
        solverManager.solveAndListen(
                SINGLETON_SOLVING_ID,
                problemId -> baseProblem,
                this::saveSolution);
        return Result.success(null);
    }

    @Override
    public Result<TimeTableVO> getTimeTable() {
        // 获取求解器状态
        SolverStatus solverStatus = getSolverStatus();

        // 获取解决方案
        TimeTable solution = timeTableMapper.selectById(SINGLETON_TIME_TABLE_ID);

        // 一次性获取所有相关数据（避免N+1查询）
        List<TeachingSession> sessions = teachingSessionMapper.selectList(null);

        // 预加载所有关联数据
        Map<Integer, Classroom> classroomMap = loadAllClassrooms();
        Map<Integer, Timeslot> timeslotMap = loadAllTimeslots();

        // 转换为VO
        TimeTableVO timeTableVO = new TimeTableVO();
        timeTableVO.setSolverStatus(solverStatus);
        timeTableVO.setScore(solution.getScore());

        // 转换会话列表
        List<TeachingSessionVO> sessionVOs = sessions.stream()
                .map(session -> convertToSessionVO(session, classroomMap, timeslotMap))
                .collect(Collectors.toList());

        timeTableVO.setSessionVOList(sessionVOs);

        return Result.success(timeTableVO);
    }

    // 加载所有教室数据
    private Map<Integer, Classroom> loadAllClassrooms() {
        List<Classroom> classrooms = classroomMapper.selectList(null);
        return classrooms.stream()
                .collect(Collectors.toMap(Classroom::getId, Function.identity()));
    }

    // 加载所有时间片数据
    private Map<Integer, Timeslot> loadAllTimeslots() {
        List<Timeslot> timeslots = timeslotMapper.selectList(null);
        return timeslots.stream()
                .collect(Collectors.toMap(Timeslot::getId, Function.identity()));
    }

    // 转换单个会话为VO
    private TeachingSessionVO convertToSessionVO(
            TeachingSession session,
            Map<Integer, Classroom> classroomMap,
            Map<Integer, Timeslot> timeslotMap
    ) {
        TeachingSessionVO vo = new TeachingSessionVO();

        // 复制基本属性
        BeanUtils.copyProperties(session, vo);

        // 转换教室
        if (session.getClassroomId() != null) {
            Classroom classroom = classroomMap.get(session.getClassroomId());
            if (classroom != null) {
                vo.setClassroom(convertToClassroomVO(classroom));
            }
        }

        // 转换时间片
        if (session.getTimeslotId() != null) {
            Timeslot timeslot = timeslotMap.get(session.getTimeslotId());
            if (timeslot != null) {
                vo.setTimeslot(convertToTimeslotVO(timeslot));
            }
        }

        return vo;
    }

    // 转换教室为VO
    private ClassroomVO convertToClassroomVO(Classroom classroom) {
        ClassroomVO vo = new ClassroomVO();
        BeanUtils.copyProperties(classroom, vo);
        return vo;
    }

    // 转换时间片为VO
    private TimeslotVO convertToTimeslotVO(Timeslot timeslot) {
        TimeslotVO vo = new TimeslotVO();
        BeanUtils.copyProperties(timeslot, vo);
        return vo;
    }

    @Override
    public Result<Void> stopSolving() {
        // 不是立即停止,而是在求解完成后停止
        solverManager.terminateEarly(SINGLETON_SOLVING_ID);
        return Result.success(null);
    }

    @Override
    public Result<SolverStatus> getStatus() {
        return Result.success(getSolverStatus());
    }

    @Override
    public Result<TimeTable> saveSolution(TimeTable timeTable) {
        //首先将session被分配到的timeslot和classroom设置相应的id
        timeTable.getSessions().forEach(session -> {
            session.setTimeslotId(session.getTimeslot().getId());
            session.setClassroomId(session.getClassroom().getId());
        });
        // 保存解决方案
        timeTableMapper.updateById(timeTable);
        // 保存session
        teachingSessionMapper.updateById(timeTable.getSessions());
        return Result.success(timeTable);
    }


    // 获取当前求解状态
    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(SINGLETON_SOLVING_ID);
    }
}
