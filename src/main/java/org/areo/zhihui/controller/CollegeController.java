package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "学院管理")
@RestController
@RequestMapping("/college")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CollegeController {

    private CollegeService collegeService;

    //查询所有学院信息
    @Operation(summary = "查询所有学院信息", description = "查询所有学院信息")
    @GetMapping("/getAllCollege")
    public ResultJson getAllCollege() {
        return collegeService.getAllCollege().toJson();
    }
}
