package org.areo.zhihui.exception;

public class MailSendErrorException extends RuntimeException{
    public MailSendErrorException(String message) {
        super(message);
    }
    public MailSendErrorException() {
        super("邮箱发送失败");
    }

}
