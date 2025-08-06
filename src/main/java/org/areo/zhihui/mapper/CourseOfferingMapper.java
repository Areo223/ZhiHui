package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.areo.zhihui.pojo.entity.CourseOffering;

import java.util.Map;

@Mapper
public interface CourseOfferingMapper extends BaseMapper<CourseOffering> {
    void reduceCurrentCapacity(String courseOfferingId);

    void increaseCurrentCapacity(String courseOfferingId);
    // 批量更新课程容量

    void batchUpdateCapacity(Map<Integer, Integer> changes);
}
