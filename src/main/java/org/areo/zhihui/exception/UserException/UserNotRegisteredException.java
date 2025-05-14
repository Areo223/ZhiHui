package org.areo.zhihui.exception.UserException;

public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException( String message ) {
        super(message);
    }
    public UserNotRegisteredException() {
        super("用户未注册");
    }
}
