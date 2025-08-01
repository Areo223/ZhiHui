package org.areo.zhihui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.areo.zhihui.pojo.dto.TimeTable;


@Mapper
public interface TimeTableMapper extends BaseMapper<TimeTable> {
    String SINGLETON_TIME_TABLE_ID = "1";
}
