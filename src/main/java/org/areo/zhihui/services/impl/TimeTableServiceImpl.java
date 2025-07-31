package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import org.areo.zhihui.mapper.TimeTableMapper;
import org.areo.zhihui.pojo.Restful;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.dto.TimeTable;
import org.areo.zhihui.services.TimeTableService;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.areo.zhihui.mapper.TimeTableMapper.SINGLETON_TIME_TABLE_ID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TimeTableServiceImpl implements TimeTableService {
    private final TimeTableMapper timeTableMapper;
    private final SolverManager<TimeTable, UUID> solverManager;
    private final SolutionManager<TimeTable, HardSoftScore> solutionManager;

    @Override
    public Result<TimeTable> testSolve(TimeTable problem) {
        UUID problemId = UUID.randomUUID();

        SolverJob<TimeTable,UUID> solverJob = solverManager.solve(problemId,problem);
        TimeTable solution;
        try {
            solution = solverJob.getFinalBestSolution();

        }catch (InterruptedException | ExecutionException e){
            return Result.failure(e);
        }
        return Result.success(solution);
    }

    @Override
    public Result<Void> solve(TimeTable problem) {
        if (problem != null) {
            timeTableMapper.insert(problem); // 保存传入的problem
        }
        // 异步求解,方法立即返回
        // 每当找到更好的解,都会调用timeTableMapper::updateById方法
        solverManager.solveAndListen(
                SINGLETON_TIME_TABLE_ID,
                timeTableMapper::selectById,
                timeTableMapper::updateById);
        return Result.success(null);
    }

    @Override
    public Result<TimeTable> getTimeTable() {
        SolverStatus solverStatus = getSolverStatus();
        TimeTable solution = timeTableMapper.selectById(SINGLETON_TIME_TABLE_ID);
        solutionManager.update(solution);
        solution.setSolverStatus(solverStatus);
        return Result.success(solution);
    }

    @Override
    public Result<Void> stopSolving() {
        // 不是立即停止,而是在求解完成后停止
        solverManager.terminateEarly(SINGLETON_TIME_TABLE_ID);
        return Result.success(null);
    }

    // 获取当前求解状态
    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(timeTableMapper.SINGLETON_TIME_TABLE_ID);
    }
}
