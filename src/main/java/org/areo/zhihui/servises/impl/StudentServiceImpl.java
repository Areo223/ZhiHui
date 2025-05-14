package org.areo.zhihui.servises.impl;

import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.vo.StudentVO;
import org.areo.zhihui.servises.StudentService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Override
    public Result<StudentVO> getStuImformation() {
        return Result.success(new StudentVO());
    }
}
