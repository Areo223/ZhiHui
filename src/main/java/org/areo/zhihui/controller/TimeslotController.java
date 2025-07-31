package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Timeslot;
import org.areo.zhihui.pojo.request.timeSlot.TimeslotAddRequest;
import org.areo.zhihui.pojo.request.timeSlot.TimeslotIdsRequest;
import org.areo.zhihui.pojo.request.timeSlot.TimeslotUpdateRequest;
import org.areo.zhihui.services.TimeslotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timeSlot")
@Tag(name = "时间片模块")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TimeslotController {

    private final TimeslotService timeslotService;

    //查询所有时间槽信息
    @GetMapping("/getAll")
    @Operation(summary = "查询所有时间槽信息", description = "查询所有时间槽信息")
    public ResultJson getAllTimeslot() {
        return timeslotService.getAllTimeslot().toJson();
    }

    //根据id查询时间槽信息
    @PostMapping("/getById")
    public ResultJson getTimeslotByIds(@Valid @RequestBody TimeslotIdsRequest request) {
        return timeslotService.getTimeslotByIds(request.getIds()).toJson();
    }

    //添加时间槽信息
    @PostMapping("/add")
    @Operation(summary = "添加时间槽信息", description = "添加时间槽信息")
    public ResultJson addTimeslot(@Valid @RequestBody TimeslotAddRequest request) {
        // 转换为实体类
        Timeslot timeslot = new Timeslot();
        BeanUtils.copyProperties(request, timeslot);
        return timeslotService.addTimeslot(timeslot).toJson();
    }

    //根据id删除时间槽信息
    @DeleteMapping("/delete")
    @Operation(summary = "根据id删除时间槽信息", description = "根据id删除时间槽信息")
    public ResultJson deleteTimeslot(@Valid @RequestBody TimeslotIdsRequest request) {
        return timeslotService.deleteTimeslot(request.getIds()).toJson();
    }

    //根据id更新时间槽信息
    @PutMapping("/update")
    @Operation(summary = "根据id更新时间槽信息", description = "根据id更新时间槽信息")
    public ResultJson updateTimeslot(@Valid @RequestBody TimeslotUpdateRequest request) {
        Timeslot timeslot = new Timeslot();
        BeanUtils.copyProperties(request, timeslot);
        return timeslotService.updateTimeslot(timeslot).toJson();
    }
}
