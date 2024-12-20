package com.personalproject.universal_pet_care.exception;

import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> appExceptionHandler(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        ApiResponse apiResponse = ApiResponse.builder()
                .data(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> exceptionHandler(Exception e) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIED;

        ApiResponse apiResponse = ApiResponse.builder()
                .data(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

}
