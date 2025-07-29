package org.areo.zhihui.services.impl;

import cn.hutool.bloomfilter.bitMap.BitMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.CacheBuilder;
import org.areo.zhihui.mapper.ScheduleMapper;
import org.areo.zhihui.pojo.entity.Schedule;
import org.areo.zhihui.services.ScheduleService;
import org.roaringbitmap.RoaringBitmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;

    @Transactional
    public void autoSchedule(){

    }
}
