package org.areo.zhihui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.request.*;
import org.areo.zhihui.pojo.request.auth.LoginRequest;
import org.areo.zhihui.pojo.request.auth.RegisterRequest;
import org.areo.zhihui.pojo.request.auth.UserUpdRequest;
import org.areo.zhihui.pojo.request.auth.UserBaseRequest;
import org.areo.zhihui.services.UserService;
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



    @Operation(summary = "管理员注册新用户", description = "注册新用户")
    @PostMapping("/addUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson addUser(@RequestBody RegisterRequest request) {
        return userService.addUser(request.getIdentifier(), request.getPassword(), request.getName(), request.getRole())
               .toJson(); // 自动转换为 JSON
    }

    @Operation(summary = "管理员删除用户", description = "删除用户")
    @DeleteMapping("/deleteUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson deleteUser(@RequestBody UserBaseRequest request) {
        return userService.deleteUser(request.getId())
              .toJson(); // 自动转换为 JSON
    }


    @Operation(summary = "管理员更新用户", description = "更新用户")
    @PutMapping("/updateUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson updateUser(@RequestBody UserUpdRequest request) {
        return userService.updateUser(request.getId(), request.getName(), request.getRole(),request.getIdentifier(),request.getPassword())
             .toJson(); // 自动转换为 JSON
    }


    // 分组查询,查询各个身份有多少人
    @Operation(summary = "管理员查询用户数量", description = "查询用户数量")
    @GetMapping("/getUserCount")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson getUserCount() {
        return userService.getUserCount()
           .toJson(); // 自动转换为 JSON
    }

//    //嵌套查询,查询用户信息和身份信息
//    @Operation(summary = "管理员查询用户信息", description = "查询用户信息")
//    @GetMapping("/getUser")
//    @RequiresRole(value = {RoleEnum.ADMIN})
//    public ResultJson getUser(@RequestBody UserBaseRequest request) {
//        return userService.getUser(request.getId())
//           .toJson(); // 自动转换为 JSON
//    }


//    @Operation(summary = "管理员查询用户", description = "查询用户")
//    @GetMapping("/getUser")
//    @RequiresRole(value = {RoleEnum.ADMIN})
//    public ResultJson getUser(@RequestBody UserBaseRequest request) {
//        return userService.getUser(request.getId())
//            .toJson(); // 自动转换为 JSON
//    }

    @Operation(summary = "用户查询自己的信息", description = "查询自己的身份和用户信息")
    @GetMapping("/getOwnUser")
    public ResultJson getOwnUser() {
        return userService.getOwnUserInfo(UserHolder.getUserId())
           .toJson(); // 自动转换为 JSON
    }

    //管理员查询所有用户
    @Operation(summary = "管理员查询指定用户", description = "查询指定用户,支持分页,支持模糊查询")
    @GetMapping("/getUsers")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson getUsers(@Valid @RequestBody UserListRequest request) {
        return userService.getUsers(request.getPageNum(), request.getPageSize(),request.getSorts(),request.getConditions())
          .toJson(); // 自动转换为 JSON
    }

    // 用户修改密码
    @Operation(summary = "修改密码", description = "用户修改自己的密码")
    @PutMapping("/passwordUpdate")
    public ResultJson passwordUpdate(@Valid @RequestBody passwordUpdateRequest request) {
        return userService.passwordUpdate(request.getOldPassword(), request.getNewPassword())
            .toJson(); // 自动转换为 JSON

    }

    //忘记密码
    @Operation(summary = "忘记密码", description = "忘记密码")
    @PutMapping("/forgetPassword")
    public ResultJson forgetPassword(@Valid @RequestBody passwordForgetRequest request) {
        return userService.forgetPassword(request.getIdentifier())
           .toJson(); // 自动转换为 JSON
    }

    //重置密码
    @Operation(summary = "重置密码", description = "重置密码")
    @PutMapping("/resetPassword")
    public ResultJson resetPassword(@RequestParam String token, @Valid @RequestBody PasswordResetRequest request) {
        return userService.resetPassword(token,request.getNewPassword())
          .toJson(); // 自动转换为 JSON
    }
}
