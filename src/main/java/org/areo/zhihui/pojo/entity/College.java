package org.areo.zhihui.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class College extends BaseEntity{
    // 学院id
    @TableId
    private Integer collegeId;
    // 学院名称
    private String collegeName;
    //学院负责人id
    private Integer collegeHeadId;
}
