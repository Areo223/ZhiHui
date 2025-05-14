package org.areo.zhihui.controller;

import lombok.RequiredArgsConstructor;

import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Course;
import org.areo.zhihui.pojo.request.AddCourseRequest;
import org.areo.zhihui.pojo.request.BaseCourseRequest;
import org.areo.zhihui.servises.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/add")
    public ResultJson addCourse(@Valid @RequestBody AddCourseRequest request) {
        Course course = new Course();
        BeanUtils.copyProperties(request,course,Course.class);
        return courseService.addCourse(course).toJson();
    }

    @DeleteMapping("/delete")
    public ResultJson deleteCourse(@Valid @RequestBody BaseCourseRequest request) {

        return courseService.deleteCourse(request.getId()).toJson();
    }

    @GetMapping("/get")
    public ResultJson getCourse(@Valid @RequestBody BaseCourseRequest request) {
        return courseService.getCourse(request.getId()).toJson();
    }



}