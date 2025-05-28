package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.pojo.vo.TeacherVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {
    Result<Void> addTeacher(Teacher teacher);

    Result<Void> deleteTeacher(List<Integer> ids);

    Result<Void> updateTeacher(Teacher teacher);

    Result<List<TeacherVO>> getAllTeacher();
}
