package org.areo.zhihui.controller;

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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TimeslotController {

    private final TimeslotService timeslotService;

    //查询所有时间槽信息
    @GetMapping("/getAll")
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
    public ResultJson addTimeslot(@Valid @RequestBody TimeslotAddRequest request) {
        Timeslot timeslot = new Timeslot();
        BeanUtils.copyProperties(request, timeslot);
        return timeslotService.addTimeslot(timeslot).toJson();
    }

    //根据id删除时间槽信息
    @DeleteMapping("/delete")
    public ResultJson deleteTimeslot(@Valid @RequestBody TimeslotIdsRequest request) {
        return timeslotService.deleteTimeslot(request.getIds()).toJson();
    }

    //根据id更新时间槽信息
    @PutMapping("/update")
    public ResultJson updateTimeslot(@Valid @RequestBody TimeslotUpdateRequest request) {
        Timeslot timeslot = new Timeslot();
        BeanUtils.copyProperties(request, timeslot);
        return timeslotService.updateTimeslot(timeslot).toJson();
    }
}
