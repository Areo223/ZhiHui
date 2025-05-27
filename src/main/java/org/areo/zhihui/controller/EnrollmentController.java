package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.pojo.request.enrollmentRequest.EnrollmentAddRequest;
import org.areo.zhihui.pojo.request.enrollmentRequest.EnrollmentBaseRequest;
import org.areo.zhihui.servises.EnrollmentService;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "选课")
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
        enrollment.setStudentId(UserHolder.getUserId());
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
}
