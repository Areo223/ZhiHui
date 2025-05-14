package org.areo.zhihui.controller;

import lombok.RequiredArgsConstructor;
import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.pojo.Restful.ResultJson;
import org.areo.zhihui.pojo.request.LoginRequest;
import org.areo.zhihui.pojo.request.RegisterRequest;
import org.areo.zhihui.pojo.request.UserAllRequest;
import org.areo.zhihui.pojo.request.UserBaseRequest;
import org.areo.zhihui.servises.UserService;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    final private UserService userService;



    @PostMapping("/login")
    public ResultJson login(@RequestBody LoginRequest request) {
        return userService.login(request.getIdentifier(), request.getPassword())
                .toJson(); // 自动转换为 JSON
    }



    @PostMapping("/addUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson addUser(@RequestBody RegisterRequest request) {
        return userService.addUser(request.getIdentifier(), request.getPassword(), request.getName(), request.getRole())
               .toJson(); // 自动转换为 JSON
    }

    @DeleteMapping("/deleteUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson deleteUser(@RequestBody UserBaseRequest request) {
        return userService.deleteUser(request.getId())
              .toJson(); // 自动转换为 JSON
    }

    @PutMapping("/updateUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson updateUser(@RequestBody UserAllRequest request) {
        return userService.updateUser(request.getId(), request.getName(), request.getRole(),request.getIdentifier(),request.getPassword())
             .toJson(); // 自动转换为 JSON
    }

    @GetMapping("/getUser")
    @RequiresRole(value = {RoleEnum.ADMIN})
    public ResultJson getUser(@RequestBody UserBaseRequest request) {
        return userService.getUser(request.getId())
            .toJson(); // 自动转换为 JSON
    }

    @GetMapping("/getOwnUser")
    public ResultJson getOwnUser() {
        return userService.getOwnUserInfo(UserHolder.getUser().getId())
           .toJson(); // 自动转换为 JSON
    }

}
