package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Student;
import org.areo.zhihui.pojo.vo.StudentVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Result<StudentVO> getStuImformation();

    Result<Void> addStu(Student student);

    Result<Void> deleteStu(Integer[] ids);

    Result<Void> updateStu(Student student);

    Result<List<StudentVO>> getAllStu();

    Result<Void> updateMyInfo(Student student);
}
