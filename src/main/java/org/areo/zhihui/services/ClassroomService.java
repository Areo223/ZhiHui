package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Classroom;
import org.areo.zhihui.pojo.vo.ClassroomVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassroomService {
    Result<ClassroomVO> addClassroom(Classroom classroom);

    Result<Void> deleteClassrooms(List<Integer> classroomIds);

    Result<ClassroomVO> updateClassroom(Classroom classroom);

    Result<List<ClassroomVO>> getClassroomByIds(List<Integer> classroomIds);

    Result<List<ClassroomVO>> getAllClassroom();
}
