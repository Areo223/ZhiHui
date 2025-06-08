package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Course;
import org.areo.zhihui.pojo.request.courseRequest.CourseAddRequest;
import org.areo.zhihui.pojo.request.courseRequest.CourseBaseRequest;
import org.areo.zhihui.pojo.request.courseRequest.CourseUptRequest;
import org.areo.zhihui.services.CourseService;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Tag(name = "课程管理")
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "管理员添加课程", description = "管理员添加课程信息")
    @PostMapping("/add")
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson addCourse(@Valid @RequestBody CourseAddRequest request) {
        Course course = new Course();
        BeanUtils.copyProperties(request,course,Course.class);
        return courseService.addCourse(course).toJson();
    }

    @Operation(summary = "管理员删除课程", description = "管理员删除课程信息")
    @DeleteMapping("/delete")
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson deleteCourse(@Valid @RequestBody CourseBaseRequest request) {

        return courseService.deleteCourse(request.getId()).toJson();
    }

    @Operation(summary = "管理员修改课程", description = "管理员修改课程信息")
    @PutMapping("/update")
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson updateCourse(@Valid @RequestBody CourseUptRequest request) {
        Course course = new Course();
        BeanUtils.copyProperties(request,course,Course.class);
        return courseService.updateCourse(course).toJson();
    }


    /*
    * 查询课程信息
    * 管理员查询所有课程信息 根据身份
    * 教师查询自己的课程信息 根据身份和课程关联的教师id
    * 学生查询自己的课程信息 有两种情况
    * 1. 学生要查询已选课程 则查询学生选择的课程信息
    * 2. 学生要查询能选择课程 则查询学生可以选择的课程信息
    *
    * */
    @Operation(summary = "查询课程", description = "管理员查询全部课程,教师查询自己的课程,学生查询自己的课程")
    @GetMapping("/get")
    public ResultJson getCourse() {
        return courseService.getCourse().toJson();
    }

    @Operation(summary = "查询已选课程", description = "查询已选课程信息")
    @RequiresRole(RoleEnum.STUDENT)
    @GetMapping("/getSelectedCourse")
    public ResultJson getSelectedCourse() {
        return courseService.getSelectedCourse().toJson();
    }



}