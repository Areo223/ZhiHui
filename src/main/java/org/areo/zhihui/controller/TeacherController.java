package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.pojo.request.TeacherUpdOwnRequest;
import org.areo.zhihui.pojo.request.TeacherUpdRequest;
import org.areo.zhihui.pojo.request.teacherRequest.TeacherAddRequest;
import org.areo.zhihui.pojo.request.teacherRequest.TeacherIdListRequest;
import org.areo.zhihui.services.TeacherService;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "教师管理")
@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TeacherController {

    private final TeacherService teacherService;

    //添加教师信息
    @Operation(summary = "管理员添加教师", description = "添加教师信息")
    @PostMapping()
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson addTeacher(@Valid @RequestBody TeacherAddRequest teacherAddRequest) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherAddRequest, teacher);
        return teacherService.addTeacher(teacher).toJson();

    }

    //删除教师信息
    @DeleteMapping()
    @RequiresRole(RoleEnum.ADMIN)
    @Operation(summary = "管理员删除教师", description = "删除教师信息")
    public ResultJson deleteTeacher(@Valid @RequestBody TeacherIdListRequest request) {
        return teacherService.deleteTeacher(request.getIds()).toJson();
    }

    //修改教师信息
    @PutMapping()
    @RequiresRole(RoleEnum.ADMIN)
    @Operation(summary = "管理员修改教师", description = "修改教师信息")
    public ResultJson updateTeacher(@Valid @RequestBody TeacherUpdRequest request) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(request, teacher);
        return teacherService.updateTeacher(teacher).toJson();
    }

    //查询所有教师信息
    @Operation(summary = "管理员查询所有教师信息", description = "查询所有教师信息")
    @GetMapping()
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson getAllTeacher() {
        return teacherService.getAllTeacher().toJson();
    }

    //教师修改自己信息
    @PutMapping("/updateTeacherInfo")
    @Operation(summary = "教师修改自己信息", description = "教师修改自己信息")
    @RequiresRole(RoleEnum.TEACHER)
    public ResultJson updateTeacherOwn(@Valid @RequestBody TeacherUpdOwnRequest request) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(request, teacher);
        return teacherService.updateTeacherOwn(teacher).toJson();
    }
}
