package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.*;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.*;
import org.areo.zhihui.pojo.vo.StudentVO;
import org.areo.zhihui.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;


    @Override
    public Result<StudentVO> getStuImformation() {
        return Result.success(new StudentVO());
    }

    @Override
    public Result<Void> addStu(Student student) {

        if (studentMapper.checkIfStudentExist(student.getUserId())) {
            //如果存在Student，则返回报错信息
            log.error("学生已存在，标识: {}",student.getId());
            return Result.failure(new CommonException("学生已存在"));
        }

        try {
            int affectedRows = studentMapper.insert(student);
            if (affectedRows == 0) {
                log.error("数据库写入失败，标识: {}",student.getUserId());
                return Result.failure(new CommonException("注册失败，数据库写入异常"));
            }
            log.info("学生成功，标识: {}",student.getUserId());
            return Result.success(null);
        }catch (Exception e){
            log.error("写入异常，标识: {} 错误:{}",student.getUserId(),e.getMessage());
            return Result.failure(e);
        }
    }
}
