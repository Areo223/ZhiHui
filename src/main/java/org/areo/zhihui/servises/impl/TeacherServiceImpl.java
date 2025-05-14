package org.areo.zhihui.servises.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.CollegeMapper;
import org.areo.zhihui.mapper.TeacherMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.servises.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;
    private final CollegeMapper collegeMapper;

    @Override
    public Result<Void> addTeacher(Teacher teacher) {
        //先判断教师是否已经存在
        if (teacherMapper.checkIfTeacherExists(teacher.getUserId())) {
            //如果存在Teacher，则返回报错信息
            log.error("教师已存在，标识: {}",teacher.getUserId());
            return Result.failure(new CommonException("教师已存在"));
        }

        //再添加教师
        try {
            int affectedRows = teacherMapper.insert(teacher);
            if (affectedRows == 0) {
                log.error("数据库写入失败，标识: {}",teacher.getUserId());
                return Result.failure(new CommonException("注册失败，数据库写入异常"));
            }
            log.info("教师成功，标识: {}",teacher.getUserId());
            return Result.success(null);
        } catch (Exception e){
            log.error("写入异常，标识: {} 错误:{}",teacher.getUserId(),e.getMessage());
            return Result.failure(e);
        }
    }
}
