package org.areo.zhihui.exception;

public class JwtErrorException extends RuntimeException{
        public JwtErrorException(String message) {
            super(message);
        }
}
