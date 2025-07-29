package org.areo.zhihui.services.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.ClassroomMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Classroom;
import org.areo.zhihui.pojo.vo.ClassroomVO;
import org.areo.zhihui.services.ClassroomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomMapper classroomMapper;

    @Override
    public Result<ClassroomVO> addClassroom(Classroom classroom) {
        //检查教室是否存在
        boolean exists = classroomMapper.exists(new QueryWrapper<Classroom>()
                .eq("room_name", classroom.getRoomName()));
        if (exists) {
            return Result.failure(new CommonException("教室已存在"));
        }
        boolean success = classroomMapper.insert(classroom)>0;
        if (!success || classroom.getId() == null) {
            return Result.failure(new CommonException("添加教室失败"));
        }

        ClassroomVO vo = new ClassroomVO();
        BeanUtils.copyProperties(classroom,vo);
        //转换成VO
        return Result.success(vo);
    }

    @Override
    public Result<Void> deleteClassrooms(List<Integer> classroomIds) {
//        //检查教室是否存在
//        boolean exists = classroomMapper.exists(new QueryWrapper<Classroom>()
//                .in("id", classroomIds));
//        if (!exists) {
//            return Result.failure(new CommonException("教室不存在"));
//        }
        boolean success = classroomMapper.deleteByIds(classroomIds) > 0;
        if (!success) {
            return Result.failure(new CommonException("删除教室失败"));
        }
        return Result.success(null);
    }

    @Override
    public Result<ClassroomVO> updateClassroom(Classroom classroom) {
        //        //检查教室是否存在
        //        boolean exists = classroomMapper.exists(new QueryWrapper<Classroom>()
        //                .eq("id", classroom.getId()));
        //        if (!exists) {
        //            return Result.failure(new CommonException("教室不存在"));
        //        }
        boolean success = classroomMapper.updateById(classroom) > 0;
        if (!success) {
            return Result.failure(new CommonException("更新教室失败"));
        }
        ClassroomVO vo = new ClassroomVO();
        BeanUtils.copyProperties(classroom,vo);
        //转换成VO
        return Result.success(vo);
    }

    @Override
    public Result<List<ClassroomVO>> getClassroomByIds(List<Integer> classroomIds) {
        List<Classroom> classrooms = classroomMapper.selectByIds(classroomIds);
        if (classrooms.isEmpty()) {
            return Result.failure(new CommonException("教室不存在"));
        }
        List<ClassroomVO> vos =classrooms
                .stream()
                .map(classroom -> {
                    ClassroomVO vo = new ClassroomVO();
                    BeanUtils.copyProperties(classroom, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(vos);
    }

    @Override
    public Result<List<ClassroomVO>> getAllClassroom() {
        List<Classroom> classrooms = classroomMapper.selectList(null);
        List<ClassroomVO> vos = classrooms
                .stream()
                .map(classroom -> {
                    ClassroomVO vo = new ClassroomVO();
                    BeanUtils.copyProperties(classroom, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(vos);
    }
//
//    // 获取课程可用的教室列表
//    private List<Classroom> getAvailableClassrooms(Course course) {
//
//        // 根据课程类型和容量筛选可用教室
//        classroomMapper.selectList(new LambdaQueryWrapper<>()
//                .eq(Classroom::getRoomType, course.ge())
//                .ge(Course::getCapacity, course.getCapacity()));
//    }
//        // 获取课程需要的教室类型
//        Set<Integer> requiredTypes = classroomMapper
//                .selectList(new QueryWrapper<Classroom>().eq())
//                .stream()
//                .map(Classroom::getRoomType)
//                .collect(Collectors.toSet());
//
//        // 获取符合条件的教室
//        return classroomMapper.selectList(null)
//                .stream()
//                .filter(r -> requiredTypes.contains(r.getRoomType()))
//                .filter(r -> r.getCapacity() >= course.getCapacity())
//                .collect(Collectors.toList());
//    }
}
