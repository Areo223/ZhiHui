package org.areo.zhihui.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CourseOfferingUpdRequest extends CourseOfferingAddRequest {
    //课程实例id
    private Integer id;
}
