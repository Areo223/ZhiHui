package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.CollegeMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.College;
import org.areo.zhihui.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CollegeServiceImpl implements CollegeService {

    private final CollegeMapper collegeMapper;

    @Override
    public Result<List<College>> getAllCollege() {
        //查询所有学院信息
        List<College> collegeList = collegeMapper.selectList(null);
        if (collegeList.isEmpty()) {
            return Result.failure(new CommonException("没有查询到任何学院信息"));
        }
        return Result.success(collegeList);

    }

    @Override
    public Result<Void> addCollege(College college) {
        //添加学院信息
        int result = collegeMapper.insert(college);
        if (result == 0) {
            return Result.failure(new CommonException("添加学院信息失败"));
        }
        return Result.success(null);
    }

    @Override
    public Result<Void> deleteColleges(List<Integer> collegeIdList) {
        //删除学院信息
        int result = collegeMapper.deleteByIds(collegeIdList);
        if (result == 0) {
            return Result.failure(new CommonException("删除学院信息失败"));
        }
        return Result.success(null);
    }

    @Override
    public Result<Void> getCollegeById(List<Integer> collegeIdList) {
        //根据id查询学院信息
        List<College> collegeList = collegeMapper.selectByIds(collegeIdList);
        if (collegeList.isEmpty()) {
            return Result.failure(new CommonException("没有查询到任何学院信息"));
        }
        return Result.success(null);
    }

    @Override
    public Result<Void> updateCollege(College college) {
        //更新学院信息
        int result = collegeMapper.updateById(college);
        if (result == 0) {
            return Result.failure(new CommonException("更新学院信息失败"));
        }
        return Result.success(null);
    }
}
