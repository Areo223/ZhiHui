package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.request.classroom.ClassroomAddRequest;
import org.areo.zhihui.pojo.request.classroom.ClassroomIdsRequest;
import org.areo.zhihui.pojo.request.classroom.ClassroomUpdRequest;
import org.areo.zhihui.pojo.entity.Classroom;
import org.areo.zhihui.services.ClassroomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "教室管理")
@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    //添加教室信息
    @Operation(summary = "添加教室信息", description = "添加教室信息")
    @PostMapping("/add")
    public ResultJson addClassroom(@Valid @RequestBody ClassroomAddRequest classroomAddRequest) {
        Classroom classroom = new Classroom();
        BeanUtils.copyProperties(classroomAddRequest, classroom, Classroom.class);
        return classroomService.addClassroom(classroom).toJson();
    }

    //删除教室信息
    @Operation(summary = "删除教室信息", description = "删除教室信息")
    @DeleteMapping("/delete")
    public ResultJson deleteClassroom(@Valid @RequestBody ClassroomIdsRequest classroomIdsRequest) {
        return classroomService.deleteClassrooms(classroomIdsRequest.getClassroomIds()).toJson();
    }

    //更新教室信息
    @Operation(summary = "更新教室信息", description = "更新教室信息")
    @PutMapping("/update")
    public ResultJson updateClassroom(@Valid @RequestBody ClassroomUpdRequest classroomUpdRequest) {
        Classroom classroom = new Classroom();
        BeanUtils.copyProperties(classroomUpdRequest, classroom, Classroom.class);
        return classroomService.updateClassroom(classroom).toJson();
    }

    //根据id查询教室信息
    @Operation(summary = "根据id查询教室信息", description = "根据id查询教室信息")
    @PostMapping("/get")
    public ResultJson getClassroomByIds(@Valid @RequestBody ClassroomIdsRequest classroomIdsRequest) {
        return classroomService.getClassroomByIds(classroomIdsRequest.getClassroomIds()).toJson();
    }

    //查询所有教室信息
    @Operation(summary = "查询所有教室信息", description = "查询所有教室信息")
    @GetMapping("/getAll")
    public ResultJson getAllClassroom() {
        return classroomService.getAllClassroom().toJson();
    }
}
