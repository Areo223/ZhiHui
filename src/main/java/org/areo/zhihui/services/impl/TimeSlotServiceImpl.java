package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.TimeSlotMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.TimeSlot;
import org.areo.zhihui.pojo.vo.TimeSlotVO;
import org.areo.zhihui.services.TimeSlotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotMapper timeSlotMapper;


    @Override
    public Result<List<TimeSlotVO>> getAllTimeSlot() {
        List<TimeSlot> timeSlotList = timeSlotMapper.selectList(null);
        List<TimeSlotVO> timeSlotVOList = timeSlotList.stream().map(timeSlot -> {
            TimeSlotVO vo = new TimeSlotVO();
            BeanUtils.copyProperties(timeSlot, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(timeSlotVOList);
    }

    @Override
    public Result<TimeSlotVO> addTimeSlot(TimeSlot timeSlot) {
        boolean success = timeSlotMapper.insert(timeSlot) > 0;
        if (!success||timeSlot.getId()==null) {
            return Result.failure(new CommonException("添加失败"));
        }
        TimeSlotVO vo = new TimeSlotVO();
        BeanUtils.copyProperties(timeSlot, vo);
        return Result.success(vo);
    }

    @Override
    public Result<Void> deleteTimeSlot(List<Integer> ids) {
        boolean success = timeSlotMapper.deleteByIds(ids) > 0;
        if (!success) {
            return Result.failure(new CommonException("删除失败"));
        }
        return Result.success(null);
    }

    @Override
    public Result<TimeSlotVO> updateTimeSlot(TimeSlot timeSlot) {
        boolean success = timeSlotMapper.updateById(timeSlot) > 0;
        if (!success) {
            return Result.failure(new CommonException("更新失败"));
        }
        TimeSlotVO vo = new TimeSlotVO();
        BeanUtils.copyProperties(timeSlot, vo);
        return Result.success(vo);
    }

    @Override
    public Result<List<TimeSlotVO>> getTimeSlotByIds(List<Integer> ids) {
        List<TimeSlot> timeSlotList = timeSlotMapper.selectByIds(ids);
        List<TimeSlotVO> timeSlotVOList = timeSlotList.stream().map(timeSlot -> {
            TimeSlotVO vo = new TimeSlotVO();
            BeanUtils.copyProperties(timeSlot, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(timeSlotVOList);
    }
}
