package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.entity.Student;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    boolean checkIfStudentExist(Integer id);

    @Override
    int insert(Student student);

    List<Student> getAllStu();
}
