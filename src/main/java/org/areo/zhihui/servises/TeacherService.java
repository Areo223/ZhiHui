package org.areo.zhihui.servises;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Teacher;
import org.springframework.stereotype.Service;

@Service
public interface TeacherService {
    Result<Void> addTeacher(Teacher teacher);
}
