package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.request.college.CollegeAddRequest;
import org.areo.zhihui.pojo.entity.College;
import org.areo.zhihui.pojo.request.college.CollegeIdsRequest;
import org.areo.zhihui.pojo.request.college.CollegeUpdRequest;
import org.areo.zhihui.services.CollegeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "学院管理")
@RestController
@RequestMapping("/college")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CollegeController {

    private CollegeService collegeService;

    //查询所有学院信息
    @Operation(summary = "查询所有学院信息", description = "查询所有学院信息")
    @GetMapping("/getAll")
    public ResultJson getAllCollege() {
        return collegeService.getAllCollege().toJson();
    }


    //新增学院信息
    @Operation(summary = "新增学院信息", description = "新增学院信息")
    @PostMapping("/add")
    public ResultJson addCollege(@Valid @RequestBody CollegeAddRequest collegeAddRequest) {
        College college = new College();
        BeanUtils.copyProperties(collegeAddRequest, college, College.class);
        return collegeService.addCollege(college).toJson();
    }

    //删除学院信息
    @Operation(summary = "删除学院信息", description = "删除学院信息")
    @DeleteMapping("/delete")
    public ResultJson deleteCollege(@Valid @RequestBody CollegeIdsRequest collegeIdsRequest) {
        return collegeService.deleteColleges(collegeIdsRequest.getCollegeIdList()).toJson();
    }

    //根据id查询学院信息
    @Operation(summary = "根据id查询学院信息", description = "根据id查询学院信息")
    @PostMapping("/getById/")
    public ResultJson getCollegeById(@Valid @RequestBody CollegeIdsRequest collegeIdsRequest) {
        return collegeService.getCollegeById(collegeIdsRequest.getCollegeIdList()).toJson();
    }

    //更新学院信息
    @Operation(summary = "更新学院信息", description = "更新学院信息")
    @PutMapping("/update")
    public ResultJson updateCollege(@Valid @RequestBody CollegeUpdRequest collegeUpdRequest) {
        College college = new College();
        BeanUtils.copyProperties(collegeUpdRequest, college, College.class);
        return collegeService.updateCollege(college).toJson();
    }
}
