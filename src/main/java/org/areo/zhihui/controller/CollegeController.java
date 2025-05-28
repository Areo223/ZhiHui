package org.areo.zhihui.controller;

import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/college")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CollegeController {

    private CollegeService collegeService;

    //查询所有学院信息
    @RequestMapping("/getAllCollege")
    public ResultJson getAllCollege() {
        return collegeService.getAllCollege().toJson();
    }
}
