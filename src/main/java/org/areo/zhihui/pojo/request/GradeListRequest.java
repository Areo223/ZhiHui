package org.areo.zhihui.pojo.request;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class GradeListRequest {
    @Nullable
    private String studentIdentifier;
    @Nullable
    private Integer courseOfferingId;
}
