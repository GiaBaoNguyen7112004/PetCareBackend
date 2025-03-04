package com.personalproject.universal_pet_care.exception;

import com.personalproject.universal_pet_care.payload.response.ApiResponse;

import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String MIN_ATTRIBUTE = "min";
    public static final String MAX_ATTRIBUTE = "max";
    public static final String REGEXP_ATTRIBUTE = "regexp";

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

        Map<String, Object> attributes = null;
        try {
            String enumKey = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
            errorCode = ErrorCode.valueOf(enumKey);
            attributes = e.getBindingResult().getFieldError()
                    .unwrap(ConstraintViolation.class).getConstraintDescriptor().getAttributes();
        } catch (Exception ex) {
            log.warn(e.getMessage());
        }

        ApiResponse apiResponse = ApiResponse.builder()
                .message((Objects.isNull(attributes)) ? errorCode.getMessage()
                        : mapAttribute(attributes, errorCode.getMessage()))
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
        log.info(e.getClass().getName());
        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .data(e.getMessage())
                .code(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    public String mapAttribute(Map<String, Object> attributes, String message) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));
        String regexp = String.valueOf(attributes.get(REGEXP_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue)
                .replace("{" + MAX_ATTRIBUTE + "}", maxValue)
                .replace("{" + REGEXP_ATTRIBUTE + "}", parseRegexp(regexp));
    }

    public static String parseRegexp(String regexp) {
        return regexp.replaceAll("[\\[\\]^$]", "")
                .replace("a-zA-Z", "characters (a-z, A-Z)")
                .replace("0-9", "number (0-9)")
                .replace("@#$%^&*", "special characters (@,#,$,%,^,&,*)");
    }

}
