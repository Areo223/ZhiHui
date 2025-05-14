package org.areo.zhihui.exception;

public class CommonException extends RuntimeException {
    public CommonException(String message) {
        super(message);
    }
    public CommonException(){
        super("服务器异常");
    }
}
