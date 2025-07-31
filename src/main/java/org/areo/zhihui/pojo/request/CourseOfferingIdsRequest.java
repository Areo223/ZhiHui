package org.areo.zhihui.pojo.request;

import lombok.Data;

import java.util.List;

@Data
public class CourseOfferingIdsRequest {
    private List<Integer> ids;
}
