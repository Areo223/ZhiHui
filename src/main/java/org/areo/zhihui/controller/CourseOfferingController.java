package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.CourseOffering;
import org.areo.zhihui.pojo.request.CourseOfferingAddRequest;
import org.areo.zhihui.pojo.request.CourseOfferingIdsRequest;
import org.areo.zhihui.pojo.request.CourseOfferingUpdRequest;
import org.areo.zhihui.services.CourseCacheService;
import org.areo.zhihui.services.CourseOfferingService;
import org.areo.zhihui.services.CourseSelectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "增加课程实例", description = "增加课程实例")
    public ResultJson addCourseOffering(@RequestBody CourseOfferingAddRequest request){
        // 转换为实体类
        CourseOffering courseOffering = new CourseOffering();
        BeanUtils.copyProperties(request,courseOffering);
        return courseOfferingService.addCourseOffering(courseOffering).toJson();
    }

    //删除课程实例
    @DeleteMapping("/delete")
    @Operation(summary = "删除课程实例", description = "删除课程实例")
    public ResultJson deleteCourseOffering(@RequestBody CourseOfferingIdsRequest request){
        return courseOfferingService.deleteCourseOffering(request.getIds()).toJson();
    }

    //获取课程实例
    @PostMapping("/get")
    @Operation(summary = "获取课程实例", description = "获取课程实例")
    public ResultJson getCourseOffering(@RequestBody CourseOfferingIdsRequest request){
        return courseOfferingService.getCourseOffering(request.getIds()).toJson();
    }

    //修改课程实例
    @PutMapping("/update")
    @Operation(summary = "修改课程实例", description = "修改课程实例")
    public ResultJson updateCourseOffering(@RequestBody CourseOfferingUpdRequest request){
        CourseOffering courseOffering = new CourseOffering();
        BeanUtils.copyProperties(request,courseOffering);
        return courseOfferingService.updateCourseOffering(courseOffering).toJson();
    }
}
