package com.personalproject.universal_pet_care.exception;

import com.personalproject.universal_pet_care.payload.response.ApiResponse;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .data(e.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY_OF_VALIDATION;
        String enumKey = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();

        if (enumKey != null && !enumKey.isEmpty()) {
            try {
                errorCode = ErrorCode.valueOf(enumKey);
            } catch (IllegalArgumentException ignored) {
                log.warn("Invalid enumKey '{}' for ErrorCode, using default: {}", enumKey, errorCode.name());
            }
        }

        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse> handlingDisabledException(DisabledException e) {
        ErrorCode errorCode = ErrorCode.ACCOUNT_DISABLED;

        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .data(e.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ApiResponse> handlingMessagingException(MessagingException e) {
        ErrorCode errorCode = ErrorCode.SENDING_EMAIL_FAILED;

        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .data(e.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<ApiResponse> handlingUnsupportedEncodingException(UnsupportedEncodingException e) {
        ErrorCode errorCode = ErrorCode.SENDING_EMAIL_FAILED;

        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .data(e.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handlingBadCredentialsException(BadCredentialsException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .data(e.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlingException(Exception e) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED;

        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .data(e.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }
}
