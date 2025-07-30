package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.CourseOffering;
import org.areo.zhihui.pojo.request.CourseOfferingAddRequest;
import org.areo.zhihui.services.CourseCacheService;
import org.areo.zhihui.services.CourseOfferingService;
import org.areo.zhihui.services.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "课程实例管理")
@RestController
@RequestMapping("/courseOffering")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseOfferingController {
    private final CourseSelectionService courseSelectionService;
    private final CourseOfferingService courseOfferingService;
    private final CourseCacheService courseCacheService;
    //增加课程实例

    @PostMapping("/add")
    public Result addCourseOffering(@RequestBody CourseOfferingAddRequest request){
        return courseOfferingService.addCourseOffering(request);
    }
}
