package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.request.CourseOfferingAddRequest;
import org.areo.zhihui.pojo.vo.CourseOfferingVO;

public interface CourseOfferingService {
    Result<CourseOfferingVO> addCourseOffering(CourseOfferingAddRequest request);
}
