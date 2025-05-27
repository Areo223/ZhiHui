package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.request.Auth.LoginRequest;
import org.areo.zhihui.pojo.request.Auth.RegisterRequest;
import org.areo.zhihui.pojo.request.Auth.UserAllRequest;
import org.areo.zhihui.pojo.request.Auth.UserBaseRequest;
import org.areo.zhihui.servises.UserService;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    final private UserService userService;



    @Operation(summary = "登录", description = "登录")
    @PostMapping("/login")
    public ResultJson login(@RequestBody LoginRequest request) {
        return userService.login(request.getIdentifier(), request.getPassword())
                .toJson(); // 自动转换为 JSON
    }



    @Operation(summary = "注册", description = "注册")
    @PostMapping("/addUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson addUser(@RequestBody RegisterRequest request) {
        return userService.addUser(request.getIdentifier(), request.getPassword(), request.getName(), request.getRole())
               .toJson(); // 自动转换为 JSON
    }

    @Operation(summary = "删除用户", description = "删除用户")
    @DeleteMapping("/deleteUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson deleteUser(@RequestBody UserBaseRequest request) {
        return userService.deleteUser(request.getId())
              .toJson(); // 自动转换为 JSON
    }


    @Operation(summary = "更新用户", description = "更新用户")
    @PutMapping("/updateUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson updateUser(@RequestBody UserAllRequest request) {
        return userService.updateUser(request.getId(), request.getName(), request.getRole(),request.getIdentifier(),request.getPassword())
             .toJson(); // 自动转换为 JSON
    }


    @Operation(summary = "查询用户", description = "查询用户")
    @GetMapping("/getUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson getUser(@RequestBody UserBaseRequest request) {
        return userService.getUser(request.getId())
            .toJson(); // 自动转换为 JSON
    }

    @Operation(summary = "查询自己的用户信息", description = "查询自己的用户信息")
    @GetMapping("/getOwnUser")
    public ResultJson getOwnUser() {
        return userService.getOwnUserInfo(UserHolder.getUserId())
           .toJson(); // 自动转换为 JSON
    }

}
