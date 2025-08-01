package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.TimeslotMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Timeslot;
import org.areo.zhihui.pojo.vo.TimeslotVO;
import org.areo.zhihui.services.TimeslotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotMapper timeslotMapper;

    @Override
    public Result<List<TimeslotVO>> getAllTimeslot() {
        List<Timeslot> timeslotList = timeslotMapper.selectList(null);
        List<TimeslotVO> timeslotVOList = timeslotList.stream().map(timeslot -> {
            TimeslotVO vo = new TimeslotVO();
            BeanUtils.copyProperties(timeslot, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(timeslotVOList);
    }

    @Override
    public Result<TimeslotVO> addTimeslot(Timeslot timeslot) {
        boolean success = timeslotMapper.insert(timeslot) > 0;
        if (!success||timeslot.getId()==null) {
            return Result.failure(new CommonException("添加失败"));
        }
        TimeslotVO vo = new TimeslotVO();
        BeanUtils.copyProperties(timeslot, vo);
        return Result.success(vo);
    }

    @Override
    public Result<Void> deleteTimeslot(List<Integer> ids) {
        boolean success = timeslotMapper.deleteByIds(ids) > 0;
        if (!success) {
            return Result.failure(new CommonException("删除失败"));
        }
        return Result.success(null);
    }

    @Override
    public Result<TimeslotVO> updateTimeslot(Timeslot timeslot) {
        boolean success = timeslotMapper.updateById(timeslot) > 0;
        if (!success) {
            return Result.failure(new CommonException("更新失败"));
        }
        TimeslotVO vo = new TimeslotVO();
        BeanUtils.copyProperties(timeslot, vo);
        return Result.success(vo);
    }

    @Override
    public Result<List<TimeslotVO>> getTimeslotByIds(List<Integer> ids) {
        List<Timeslot> timeslotList = timeslotMapper.selectByIds(ids);
        List<TimeslotVO> timeslotVOList = timeslotList.stream().map(timeslot -> {
            TimeslotVO vo = new TimeslotVO();
            BeanUtils.copyProperties(timeslot, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(timeslotVOList);
    }
}
