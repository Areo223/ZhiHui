package org.areo.zhihui.utils;

import org.areo.zhihui.pojo.entity.User;

public class UserHolder {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    //设置用户对象的方法
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }
    //获取用户对象的方法
    public static User getUser() {
        return userThreadLocal.get();
    }

    public static Integer getUserId() {
        return userThreadLocal.get().getId();
    }
    //清除用户对象的方法
    public static void removeUser() {
        userThreadLocal.remove();
    }
}
