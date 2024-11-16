package com.personalproject.universal_pet_care.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    USER_EXIST(1000, "User existed", HttpStatus.BAD_REQUEST),
    NO_DATA_FOUND(1001, "No data was found", HttpStatus.NOT_FOUND),
    CANNOT_UPDATE(1002, "Resource can not be updated", HttpStatus.BAD_REQUEST),
    REVIEW_YOURSELF(1003, "Veterinarian can not review himself", HttpStatus.BAD_REQUEST),
    ALREADY_REVIEWED(1004,"You already reviewed this veterinarian", HttpStatus.CONFLICT),
    REVIEW_NOT_COMPLETED(1005, "Only reviewer who has complete appointment can review",
            HttpStatus.NOT_ACCEPTABLE);


    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
