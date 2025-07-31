package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.CourseOfferingMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.CourseOffering;
import org.areo.zhihui.pojo.vo.CourseOfferingVO;
import org.areo.zhihui.services.CourseOfferingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseOfferingServiceImpl implements CourseOfferingService {
    private final CourseOfferingMapper courseOfferingMapper;


    @Override
    public Result<CourseOfferingVO> addCourseOffering(CourseOffering courseOffering) {
        //检查课程是否存在
        boolean exists = courseOfferingMapper.exists(new QueryWrapper<CourseOffering>()
                .eq("course_id", courseOffering.getCourseId()));
        if (exists) {
            return Result.failure(new CommonException("课程已存在"));
        }
        courseOffering.setCurrentCapacity(courseOffering.getMaxCapacity());
        boolean success = courseOfferingMapper.insert(courseOffering) > 0;
        if (!success || courseOffering.getId() == null) {
            return Result.failure(new CommonException("添加课程实例失败"));
        }
        CourseOfferingVO vo = new CourseOfferingVO();
        BeanUtils.copyProperties(courseOffering,vo);
        return Result.success(vo);
    }

    @Override
    public Result<Void> deleteCourseOffering(List<Integer> ids) {
        boolean success = courseOfferingMapper.deleteByIds(ids) > 0;
        if (!success) {
            return Result.failure(new CommonException("删除课程实例失败"));
        }
        return Result.success(null);
    }

    @Override
    public Result<List<CourseOfferingVO>> getCourseOffering(List<Integer> ids) {
        List<CourseOffering> courseOfferings = courseOfferingMapper.selectByIds(ids);
        if (courseOfferings.isEmpty()) {
            return Result.failure(new CommonException("课程实例不存在"));
        }
        List<CourseOfferingVO> vos = courseOfferings.stream().map(courseOffering -> {
            CourseOfferingVO vo = new CourseOfferingVO();
            BeanUtils.copyProperties(courseOffering,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(vos);
    }

    @Override
    public Result<CourseOfferingVO> updateCourseOffering(CourseOffering courseOffering) {
        CourseOfferingVO vo = new CourseOfferingVO();
        BeanUtils.copyProperties(courseOffering,vo);
        boolean success = courseOfferingMapper.updateById(courseOffering) > 0;
        if (!success) {
            return Result.failure(new CommonException("更新课程实例失败"));
        }
        return Result.success(vo);
    }

}
