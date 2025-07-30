package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.entity.TeachingSession;

@Mapper
public interface TeachingClassMapper extends BaseMapper<TeachingSession> {
    void reduceCurrentCapacity(String teachingClassCode);

    void increaseCurrentCapacity(String teachingClassCode);
}
