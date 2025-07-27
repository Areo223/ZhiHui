package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.entity.TeachingClass;

@Mapper
public interface TeachingClassMapper extends BaseMapper<TeachingClass> {
    void reduceCurrentCapacity(String teachingClassCode);
}
