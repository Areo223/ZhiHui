package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.pojo.request.GradeAddRequest;
import org.areo.zhihui.pojo.request.GradeListRequest;
import org.areo.zhihui.pojo.request.enrollment.EnrollmentAddRequest;
import org.areo.zhihui.pojo.request.enrollment.EnrollmentBaseRequest;
import org.areo.zhihui.services.EnrollmentService;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "选课模块")
@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentController {

    private final EnrollmentService enrollmentService;


    @Operation(summary = "学生选课", description = "学生选课")
    @PostMapping("/select")
    @RequiresRole(value = {RoleEnum.STUDENT})
    public ResultJson selectCourse(@Valid @RequestBody EnrollmentBaseRequest request){
        Enrollment enrollment = new Enrollment();
        BeanUtils.copyProperties(request, enrollment);
        enrollment.setStudentIdentifier(UserHolder.getUser().getIdentifier());
       return enrollmentService.selectCourse(enrollment).toJson();
    }

    @Operation(summary = "管理员添加选课", description = "管理员添加选课")
    @PostMapping("/add")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson addEnrollment(@Valid @RequestBody EnrollmentAddRequest request){
        Enrollment enrollment = new Enrollment();
        BeanUtils.copyProperties(request, enrollment);
       return enrollmentService.selectCourse(enrollment).toJson();
    }


    @Operation(summary = "学生退课",description = "学生退课")
    @PostMapping("/withdraw")
    @RequiresRole(value = {RoleEnum.STUDENT})
    public ResultJson withdrawEnrollment(@Valid @RequestBody EnrollmentBaseRequest request){
        Enrollment enrollment = new Enrollment();
        BeanUtils.copyProperties(request, enrollment);
        enrollment.setStudentIdentifier(UserHolder.getUser().getIdentifier());
        return enrollmentService.withdrawCourse(enrollment).toJson();
    }

    @Operation(summary = "查询已选课程", description = "查询已选课程信息")
    @RequiresRole(RoleEnum.STUDENT)
    @GetMapping("/getSelectedCourse")
    public ResultJson getSelectedCourse() {
        return enrollmentService.getSelectedCourse().toJson();
    }

    @Operation(summary = "同步数据库数据到redis", description = "同步数据库数据到redis")
    @RequiresRole(RoleEnum.ADMIN)
    @PostMapping("/async")
    public ResultJson async() {
        return enrollmentService.asyncDBToRedis().toJson();
    }





    @Operation(summary = "教师录入学生成绩",description = "教师录入学生成绩")
    @RequiresRole(RoleEnum.TEACHER)
    @PostMapping("/grade")
    public ResultJson grade(@Valid @RequestBody GradeAddRequest request){
        return enrollmentService.setGrade(
                request.getStudentIdentifier(),
                request.getCourseOfferingId(),
                request.getGrade()).toJson();
    }

    @Operation(summary = "教师修改成绩",description = "教师修改学生成绩")
    @PutMapping("/grade")
    @RequiresRole(RoleEnum.TEACHER)
    public ResultJson updateGrade(@Valid @RequestBody GradeAddRequest request){
        return enrollmentService.updGrade(
                request.getStudentIdentifier(),
                request.getCourseOfferingId(),
                request.getGrade()).toJson();
    }

    @Operation(summary = "查询学生成绩",description = "查询学生成绩")
    @GetMapping("/getGrade")
    public ResultJson getGrade(@Valid @RequestBody GradeListRequest request){
        return enrollmentService.getGrade(request.getStudentIdentifier(),request.getCourseOfferingId()).toJson();
    }
}
