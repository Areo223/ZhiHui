package org.areo.zhihui.exception.UserException;

public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException( String message ) {
        super(message);
    }
}
