package com.personalproject.universal_pet_care.exception;

import com.personalproject.universal_pet_care.payload.response.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        ApiResponse apiResponse = ApiResponse.builder()
                .data(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_DATA;

        ApiResponse apiResponse = ApiResponse.builder()
                .data(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse> handlingDisabledException(DisabledException e) {
        ErrorCode errorCode = ErrorCode.ACCOUNT_DISABLED;

        ApiResponse apiResponse = ApiResponse.builder()
                .data(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlingException(Exception e) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIED;

        ApiResponse apiResponse = ApiResponse.builder()
                .data(e.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

}
