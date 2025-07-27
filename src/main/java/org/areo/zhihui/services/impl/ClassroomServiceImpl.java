package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.mapper.ClassroomMapper;
import org.areo.zhihui.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomMapper classroomMapper;
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
