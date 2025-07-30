package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.mapper.CourseOfferingMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.request.CourseOfferingAddRequest;
import org.areo.zhihui.pojo.vo.CourseOfferingVO;
import org.areo.zhihui.services.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseOfferingServiceImpl implements CourseOfferingService {
    private final CourseOfferingMapper courseOfferingMapper;


    @Override
    public Result<CourseOfferingVO> addCourseOffering(CourseOfferingAddRequest request) {
        return null;
    }
}
