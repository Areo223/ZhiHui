package org.areo.zhihui.exception;

import org.areo.zhihui.pojo.Restful;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Restful.ResultJson> handleAccessDeniedException(AccessDeniedException ex) {
        Restful.ResultJson error = Restful.error(
                Restful.INTERNAL_ERROR_CODE,
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Restful.ResultJson> handleGenericException(Exception ex) {
        Restful.ResultJson error = Restful.error(
                Restful.INTERNAL_ERROR_CODE,
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}