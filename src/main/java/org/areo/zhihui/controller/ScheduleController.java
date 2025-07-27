package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@Tag(name = "课程排课", description = "课程排课相关接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleController {

    private final ScheduleService scheduleService;
}
