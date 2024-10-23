package com.company.ProductService.model.exception;

import com.company.ProductService.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductServiceCustomException.class)
    public ResponseEntity<ErrorDTO> handleProductServiceException(ProductServiceCustomException exception) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode()).build(),
                HttpStatus.NOT_FOUND);
    }
}
