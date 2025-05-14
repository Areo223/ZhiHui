package org.areo.zhihui.exception.UserException;

public class UserAccessDeniedException extends RuntimeException{
    public UserAccessDeniedException(String message) {
        super(message);
    }
}
