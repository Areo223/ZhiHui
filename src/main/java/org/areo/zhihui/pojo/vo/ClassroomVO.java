package org.areo.zhihui.pojo.vo;

import lombok.Data;
import org.areo.zhihui.utils.enums.RoomTypeEnum;

@Data
public class ClassroomVO {
    //教室id
    private Integer id;
    //教室名称
    private String roomName;
    //教室容量
    private Integer capacity;
    //教室类型
    private RoomTypeEnum roomType;
}
