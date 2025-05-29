package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.TeacherMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.pojo.vo.TeacherVO;
import org.areo.zhihui.services.TeacherService;
import org.areo.zhihui.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;

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

    @Override
    public Result<Void> deleteTeacher(List<Integer> ids) {
        // 先检查教师是否有关联的课程
        // TODO: 检查教师是否有关联的职位
        for (Integer id : ids) {
            if (teacherMapper.checkIfTeacherHasCourse(id)) {
                log.error("教师存在关联，标识: {}",id);
                return Result.failure(new CommonException("教师存在关联，无法删除"));
            }
        }
        // 再删除教师
        try {
            int affectedRows = teacherMapper.deleteByIds(ids);
            if (affectedRows == 0) {
                log.error("数据库写入失败，标识: {}",ids);
                return Result.failure(new CommonException("删除失败，数据库写入异常"));
            }
            log.info("教师成功，标识: {}",ids);
            return Result.success(null);
        }
        catch (Exception e){
            log.error("写入异常，标识: {} 错误:{}",ids,e.getMessage());
            return Result.failure(e);
        }
    }

    @Override
    public Result<Void> updateTeacher(Teacher teacher) {
        // 先检查教师是否存在
        if (!teacherMapper.checkIfTeacherExists(teacher.getUserId())) {
            log.error("教师不存在，标识: {}",teacher.getUserId());
            return Result.failure(new CommonException("教师不存在"));
        }
        // 再更新教师
        try {
            int affectedRows = teacherMapper.update(teacher, new QueryWrapper<Teacher>().eq("user_id", teacher.getUserId()));
            if (affectedRows == 0) {
                log.error("数据库写入失败，标识: {}",teacher.getUserId());
                return Result.failure(new CommonException("更新失败，数据库写入异常"));
            }
            log.info("教师成功，标识: {}",teacher.getUserId());
            return Result.success(null);
        }
        catch (Exception e){
            log.error("写入异常，标识: {} 错误:{}",teacher.getUserId(),e.getMessage());
            return Result.failure(e);
        }
    }

    @Override
    public Result<List<TeacherVO>> getAllTeacher() {
        try {
            List<Teacher> teachers = teacherMapper.selectAllTeacher();
            log.info("教师成功，标识: {}",teachers);
            List<TeacherVO> teachersVO = teachers.stream().map(teacher -> {
                TeacherVO teacherVO = new TeacherVO();
                BeanUtils.copyProperties(teacher, teacherVO);
                return teacherVO;
            }).toList();
            return Result.success(teachersVO);
        }
        catch (Exception e){
            log.error("查询异常，错误:{}",e.getMessage());
            return Result.failure(e);
        }
    }

    @Override
    public Result<Void> updateTeacherOwn(Teacher teacher) {
        // 先检查教师是否存在
        if (!teacherMapper.checkIfTeacherExists(UserHolder.getUserId())) {
            log.error("教师不存在，标识: {}",teacher.getUserId());
            return Result.failure(new CommonException("教师不存在"));
        }
        // 再更新教师
        try {
            int affectedRows = teacherMapper.update(teacher, new QueryWrapper<Teacher>().eq("user_id", UserHolder.getUserId()));
            if (affectedRows == 0) {
                log.error("数据库写入失败，标识: {}",teacher.getUserId());
                return Result.failure(new CommonException("更新失败，数据库写入异常"));
            }
            log.info("教师成功，标识: {}",teacher.getUserId());
            return Result.success(null);
        }
        catch (Exception e){
            log.error("写入异常，标识: {} 错误:{}",teacher.getUserId(),e.getMessage());
            return Result.failure(e);
        }
    }
}
