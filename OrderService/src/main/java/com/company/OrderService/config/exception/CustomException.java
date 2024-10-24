package com.company.OrderService.config.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private String errorCode;
    private Integer status;

    public CustomException(String message, String errorCode, Integer status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
