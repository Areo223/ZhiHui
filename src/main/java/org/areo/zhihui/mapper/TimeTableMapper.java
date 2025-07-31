package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.dto.TimeTable;

import java.util.UUID;

@Mapper
public interface TimeTableMapper extends BaseMapper<TimeTable> {
    public UUID SINGLETON_TIME_TABLE_ID = UUID.randomUUID();

    int insert(TimeTable timeTable);

    int updateByIds(TimeTable timeTable);
}
