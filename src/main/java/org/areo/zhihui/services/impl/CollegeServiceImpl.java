package org.areo.zhihui.services.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
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
}
