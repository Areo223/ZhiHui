package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.*;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.*;
import org.areo.zhihui.pojo.vo.StudentVO;
import org.areo.zhihui.services.StudentService;
import org.areo.zhihui.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public Result<Void> deleteStu(Integer[] ids) {
        try {
            log.info("正在删除学生，标识: {}", (Object[]) ids);
            int affectRows = studentMapper.deleteByIds(Arrays.asList(ids));
            if (affectRows == 0) {
                log.error("删除失败，标识: {}", (Object[]) ids);
                return Result.failure(new CommonException("删除失败"));
            }
            log.info("删除成功，标识: {}", (Object[]) ids);
            return Result.success(null);
        }catch (Exception e){
            log.error("删除异常，标识: {} 错误:{}",ids,e.getMessage());
            return Result.failure(e);
        }
    }

    @Override
    public Result<Void> updateStu(Student student) {
        // 检查学生是否存在
        if (!studentMapper.checkIfStudentExist(student.getUserId())) {
            log.error("学生不存在，标识: {}",student.getUserId());
            return Result.failure(new CommonException("学生不存在"));
        }
        try {
            int affectRows = studentMapper.update(student,new QueryWrapper<Student>().eq("user_id",student.getUserId()));
            if (affectRows == 0) {
                log.error("更新失败，标识: {}",student.getUserId());
                return Result.failure(new CommonException("更新失败"));
            }
            log.info("更新成功，标识: {}",student.getUserId());
            return Result.success(null);
        }catch (Exception e){
            log.error("更新异常，标识: {} 错误:{}",student.getUserId(),e.getMessage());
            return Result.failure(e);
        }
    }

    @Override
    public Result<List<StudentVO>> getAllStu() {
        try {
            List<Student> students = studentMapper.getAllStu();
            List<StudentVO> studentVOS = students.stream().map(student -> {
                StudentVO studentVO = new StudentVO();
                BeanUtils.copyProperties(student, studentVO);
                return studentVO;
            }).toList();
            return Result.success(studentVOS);
        }catch (Exception e){
            log.error("查询异常，错误:{}",e.getMessage());
            return Result.failure(e);
        }
    }

    @Override
    public Result<Void> updateMyInfo(Student student) {
        if (!studentMapper.checkIfStudentExist(UserHolder.getUserId())) {
            log.error("学生不存在，标识: {}",UserHolder.getUserId());
            return Result.failure(new CommonException("学生不存在"));
        }
        try {
            int affectRows = studentMapper.update(student,new QueryWrapper<Student>().eq("user_id",UserHolder.getUserId()));
            if (affectRows == 0) {
                log.error("更新失败，标识: {}",UserHolder.getUserId());
                return Result.failure(new CommonException("更新失败"));
            }
            log.info("更新成功，标识: {}",UserHolder.getUserId());
            return Result.success(null);
        }catch (Exception e){
            log.error("更新异常，标识: {} 错误:{}",UserHolder.getUserId(),e.getMessage());
            return Result.failure(e);
        }
    }
}
