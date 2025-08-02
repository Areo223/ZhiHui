package org.areo.zhihui.pojo.request;

import lombok.Data;

@Data
public class GradeAddRequest {
    private String studentIdentifier;
    private Integer courseOfferingId;
    private Integer grade;
}
