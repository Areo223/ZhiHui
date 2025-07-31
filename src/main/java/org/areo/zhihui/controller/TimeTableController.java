package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.mapper.TimeTableMapper;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.dto.TimeTable;

import org.areo.zhihui.services.TimeTableService;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.areo.zhihui.mapper.TimeTableMapper.SINGLETON_TIME_TABLE_ID;

@RestController
@Tag(name = "排课模块")
@RequestMapping("/timeTable")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TimeTableController {

    private final TimeTableService timeTableService;

    @PostMapping("/testSolve")
    @Operation(summary = "排课", description = "排课")
    public ResultJson testSolve(@RequestBody TimeTable problem){
        return timeTableService.testSolve(problem).toJson();
    }

    @PostMapping("/solve")
    @Operation(summary = "开始排课求解", description = "开始排课求解,异步求解,结果保存在数据库")
    public ResultJson solve(@RequestBody(required = false) TimeTable problem) {
        return timeTableService.solve(problem).toJson();
    }

    @GetMapping()
    @Operation(summary = "获取当前排课方案", description = "获取当前排课方案,可以轮询获取最新排课方案")
    public ResultJson getTimeTable(){
        return timeTableService.getTimeTable().toJson();
    }

    @PostMapping("/stopSolving")
    @Operation(summary = "停止排课求解", description = "停止排课求解,不是立即停止,而是在求解完成后停止")
    public ResultJson stopSolving() {
        return timeTableService.stopSolving().toJson();
    }

}
