package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.mapper.ScheduleMapper;
import org.areo.zhihui.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;

    @Transactional
    public void autoSchedule(){

    }
}
