package org.areo.zhihui.controller;


import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.request.UserBaseRequest;
import org.areo.zhihui.servises.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
