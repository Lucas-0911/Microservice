package com.company.OrderService.config.exception;

import com.company.OrderService.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleProductServiceException(CustomException exception) {
        return new ResponseEntity<>(new com.company.OrderService.external.response.ErrorResponse().builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(),
                HttpStatus.valueOf(exception.getStatus())
        );
    }
}
