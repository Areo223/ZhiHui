package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.entity.Enrollment;

@Mapper
public interface EnrollmentMapper extends BaseMapper<Enrollment> {

    boolean checkIfStudentHasSelectedCourse(String studentIdentifier, String teachingClassCode);
}
