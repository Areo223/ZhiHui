package org.areo.zhihui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.TimeSlot;
import org.areo.zhihui.pojo.request.timeSlot.TimeSlotAddRequest;
import org.areo.zhihui.pojo.request.timeSlot.TimeSlotIdsRequest;
import org.areo.zhihui.pojo.request.timeSlot.TimeSlotUpdateRequest;
import org.areo.zhihui.services.TimeSlotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timeSlot")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    //查询所有时间槽信息
    @GetMapping("/getAll")
    public ResultJson getAllTimeSlot() {
        return timeSlotService.getAllTimeSlot().toJson();
    }

    //根据id查询时间槽信息
    @PostMapping("/getById")
    public ResultJson getTimeSlotByIds(@Valid @RequestBody TimeSlotIdsRequest request) {
        return timeSlotService.getTimeSlotByIds(request.getIds()).toJson();
    }

    //添加时间槽信息
    @PostMapping("/add")
    public ResultJson addTimeSlot(@Valid @RequestBody TimeSlotAddRequest request) {
        TimeSlot timeSlot = new TimeSlot();
        BeanUtils.copyProperties(request, timeSlot);
        return timeSlotService.addTimeSlot(timeSlot).toJson();
    }

    //根据id删除时间槽信息
    @DeleteMapping("/delete")
    public ResultJson deleteTimeSlot(@Valid @RequestBody TimeSlotIdsRequest request) {
        return timeSlotService.deleteTimeSlot(request.getIds()).toJson();
    }

    //根据id更新时间槽信息
    @PutMapping("/update")
    public ResultJson updateTimeSlot(@Valid @RequestBody TimeSlotUpdateRequest request) {
        TimeSlot timeSlot = new TimeSlot();
        BeanUtils.copyProperties(request, timeSlot);
        return timeSlotService.updateTimeSlot(timeSlot).toJson();
    }
}
