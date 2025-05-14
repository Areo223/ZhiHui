package org.areo.zhihui.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.areo.zhihui.annotation.ErrorCode;

public class ValidatorException{

    @ErrorCode(value = 410,message = "密码格式错误")
    public static class InvalidPasswordException extends RuntimeException{
        public InvalidPasswordException() {
            super("密码强度不符合要求");
        }
        public InvalidPasswordException(String message) {
            super(message);
        }

    }

    @ErrorCode(value = 401,message = "密码错误")
    public static class ErrorPasswordException extends RuntimeException {
        public ErrorPasswordException() {
            super("密码错误");
        }
        public ErrorPasswordException(String message) {
            super(message);
        }
    }
}
