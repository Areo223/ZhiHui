package org.areo.zhihui.exception.UserException;


import org.areo.zhihui.annotation.ErrorCode;

@ErrorCode(value = 401, message = "用户未登录")
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){
        super(message);
    }
}
