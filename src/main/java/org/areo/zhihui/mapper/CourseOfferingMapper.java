package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.entity.CourseOffering;

@Mapper
public interface CourseOfferingMapper extends BaseMapper<CourseOffering> {
    void reduceCurrentCapacity(String courseOfferingId);

    void increaseCurrentCapacity(String courseOfferingId);
}
