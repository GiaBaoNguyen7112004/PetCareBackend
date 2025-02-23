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
    UPDATE_FAILED(1002, "Resource can not be updated", HttpStatus.BAD_REQUEST),
    REVIEW_YOURSELF(1003, "Veterinarian can not review himself", HttpStatus.BAD_REQUEST),
    ALREADY_REVIEWED(1004, "You already reviewed this veterinarian", HttpStatus.CONFLICT),
    REVIEW_NOT_COMPLETED(1005, "Only reviewer who has complete appointment can review",
            HttpStatus.NOT_ACCEPTABLE),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED(1007, "Uncategorized", HttpStatus.BAD_REQUEST),
    INVALID_DATA(1008, "Invalid data", HttpStatus.BAD_REQUEST),
    ACCOUNT_DISABLED(1009, "Account disabled", HttpStatus.UNAUTHORIZED),
    INVALID_VERIFICATION_TOKEN(1010, "Invalid verification token", HttpStatus.UNAUTHORIZED),
    INITIATE_UTILITIES(1011, "Initiate utilities", HttpStatus.UNAUTHORIZED),
    ALREADY_VERIFIED_ACCOUNT(1012, "Account has already been verified", HttpStatus.CONFLICT),
    EXPIRED_TOKEN(1013, "Token is expired", HttpStatus.UNAUTHORIZED),
    VALIDATE_TOKEN(1014, "Token validation failed", HttpStatus.UNAUTHORIZED),
    SENDING_EMAIL_FAILED(1015, "Sending email failed", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_FOUND(1016, "Appointment not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1017, "Role not found", HttpStatus.NOT_FOUND),
    INVALID_TIME(1018, "Invalid time", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1019, "User not found", HttpStatus.NOT_FOUND),
    APPOINTMENT_NOT_APPROVED(1020, "Appointment is still not approved", HttpStatus.CONFLICT),
    APPOINTMENT_UPDATE_FAILED(1021, "Cannot update appointment being approved", HttpStatus.CONFLICT),
    ;

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
