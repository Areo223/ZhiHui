package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.pojo.request.teacherRequest.TeacherAddRequest;
import org.areo.zhihui.pojo.request.teacherRequest.TeacherBaseRequest;
import org.areo.zhihui.servises.TeacherService;
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
    @PostMapping()
    public ResultJson addTeacher(@Valid @RequestBody TeacherAddRequest teacherAddRequest) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherAddRequest, teacher);
        return teacherService.addTeacher(teacher).toJson();

    }

    //删除教师信息
    @DeleteMapping()
    public ResultJson deleteTeacher(@Valid @RequestBody TeacherBaseRequest request) {
        return teacherService.deleteTeacher(request.getIds()).toJson();
    }
}
