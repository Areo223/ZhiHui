package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.entity.Student;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.pojo.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Select("SELECT MAX(num) FROM user FOR UPDATE")
//    Integer selectMaxNumForUpdate();
//
//    @Select("SELECT COUNT(*) FROM user WHERE num = #{num}")
//    int countByNum(@Param("num") Integer num);
//
//    default void verifyNumUnique(Integer num) {
//        if (countByNum(num) > 0) {
//            throw new RuntimeException("序号冲突，请重试");
//        }
//    }

    Student getOwnStudentInfo(Integer id);

    Teacher getOwnTeacherInfo(Integer id);


}
