package org.areo.zhihui.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Student;
import org.areo.zhihui.pojo.request.student.StudentAddRequest;
import org.areo.zhihui.pojo.request.student.StudentIdListRequest;
import org.areo.zhihui.pojo.request.student.StudentUpdOwnRequest;
import org.areo.zhihui.pojo.request.student.StudentUpdRequest;
import org.areo.zhihui.services.StudentService;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "学生管理")
@RestController
@RequestMapping("/stu")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StudentController {

    private final StudentService studentService;
//
//    @RequestMapping("/getStu")
//    public ResultJson getStu(@RequestBody UserBaseRequest userBaseRequest) {
//        return studentService.getStuImformation(userBaseRequest.getId()).toJson();
//    }

    @Operation(summary = "管理员添加学生", description = "添加学生信息")
    @PostMapping()
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson addStu(@Valid @RequestBody StudentAddRequest studentAddRequest) {
        Student student = new Student();
        BeanUtils.copyProperties(studentAddRequest, student, Student.class);
        return studentService.addStu(student).toJson();
    }

    // 删除学生
    @DeleteMapping()
    @Operation(summary = "管理员删除学生", description = "删除学生信息")
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson deleteStu(@Valid @RequestBody StudentIdListRequest studentIdListRequest) {
        return studentService.deleteStu(studentIdListRequest.getIds()).toJson();
    }

    // 修改学生
    @PutMapping()
    @Operation(summary = "管理员修改学生", description = "修改学生信息")
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson updateStu(@Valid @RequestBody StudentUpdRequest studentUpdRequest) {
        Student student = new Student();
        BeanUtils.copyProperties(studentUpdRequest, student, Student.class);
        return studentService.updateStu(student).toJson();
    }

    // 查询所有学生信息
    @GetMapping()
    @Operation(summary = "管理员查询所有学生", description = "查询所有学生信息")
    @RequiresRole(RoleEnum.ADMIN)
    public ResultJson getAllStu() {
        return studentService.getAllStu().toJson();

    }

    //用户修改自己信息
    @PutMapping("/updateStuInfo")
    @Operation(summary = "学生修改自己信息", description = "修改学生信息")
    @RequiresRole(RoleEnum.STUDENT)
    public ResultJson updateMyInfo(@Valid @RequestBody StudentUpdOwnRequest studentUpdOwnRequest) {
        Student student = new Student();
        BeanUtils.copyProperties(studentUpdOwnRequest, student, Student.class);
        return studentService.updateMyInfo(student).toJson();
    }
}
