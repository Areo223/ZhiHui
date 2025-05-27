package org.areo.zhihui.servises;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {
    Result<Void> addTeacher(Teacher teacher);

    Result<Void> deleteTeacher(List<Integer> ids);
}
