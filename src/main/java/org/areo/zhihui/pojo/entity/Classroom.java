package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.areo.zhihui.utils.enums.RoomTypeEnum;

@Data
@TableName(value = "classroom")
public class Classroom extends BaseEntity{
    //教室id
    private Integer id;
    //教室名称
    private String roomName;
    //教室容量
    private Integer capacity;
    //教室类型
    private RoomTypeEnum roomType;
}
