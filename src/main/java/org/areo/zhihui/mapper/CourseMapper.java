package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.entity.Course;

import java.util.List;


@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    List<Course> listTeacherCourse(Integer id);
}
