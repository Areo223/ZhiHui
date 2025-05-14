package org.areo.zhihui.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Student;
import org.areo.zhihui.pojo.request.StudentAddRequest;
import org.areo.zhihui.servises.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/addStu")
    public ResultJson addStu(@Valid @RequestBody StudentAddRequest studentAddRequest) {
        Student student = new Student();
        BeanUtils.copyProperties(studentAddRequest, student, Student.class);
        return studentService.addStu(student).toJson();
    }

}
