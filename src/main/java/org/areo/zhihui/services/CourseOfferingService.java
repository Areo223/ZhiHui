package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.CourseOffering;
import org.areo.zhihui.pojo.vo.CourseOfferingVO;

import java.util.List;

public interface CourseOfferingService {
    Result<CourseOfferingVO> addCourseOffering(CourseOffering courseOffering);

    Result<Void> deleteCourseOffering(List<Integer> ids);

    Result<List<CourseOfferingVO>> getCourseOffering(List<Integer> ids);

    Result<CourseOfferingVO> updateCourseOffering(CourseOffering courseOffering);
}
