package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.entity.Teacher;
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
   boolean checkIfTeacherExists(int id);

   @Override
   int insert(Teacher teacher);
}
