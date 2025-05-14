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

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ExistsValidator.ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        List<ExistsValidator.ValidationErrorResponse.FieldErrorDetail> errors = ex.getBindingResult().getFieldErrors()
//                .stream()
//                .map(error -> {
//                    ExistsValidator.ValidationErrorResponse.FieldErrorDetail detail = new ExistsValidator.ValidationErrorResponse.FieldErrorDetail();
//                    detail.setField(error.getField());
//                    detail.setRejectedValue(error.getRejectedValue());
//                    detail.setMessage(error.getDefaultMessage());
//                    return detail;
//                })
//                .collect(Collectors.toList());
//
//        ExistsValidator.ValidationErrorResponse response = new ExistsValidator.ValidationErrorResponse();
//        response.setStatus(HttpStatus.BAD_REQUEST.value());
//        response.setMessage("请求参数验证失败");
//        response.setErrors(errors);
//        response.setTimestamp(System.currentTimeMillis());
//
//        return ResponseEntity.badRequest().body(response);
//    }

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