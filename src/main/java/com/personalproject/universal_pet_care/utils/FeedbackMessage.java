package com.personalproject.universal_pet_care.utils;

import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import lombok.Getter;

@Getter
public class FeedbackMessage {
    public static final String CREATE_SUCCESS = "Created successfully";
    public static final String UPDATE_SUCCESS = "Updated successfully";
    public static final String DELETE_SUCCESS = "Deleted successfully";
    public static final String GET_SUCCESS = "Get successfully";

    public static final String INITIATE_ADMIN = "admin was created with default password: admin, please change it!";

    //        Appointment
    public static final String CANCEL_APPOINTMENT_SUCCESS = "Cancel appointment successfully";
    public static final String APPROVE_APPOINTMENT_SUCCESS = "Approve appointment successfully";
    public static final String DECLINE_APPOINTMENT_SUCCESS = "Decline appointment successfully";
    public static final String APPOINTMENT_COMPLETED = "Appointment completed successfully";

    //      Authentication
    public static final String AUTHENTICATE_SUCCESS = "Authenticated successfully";
    public static final String VALID_TOKEN = "Token is valid";
    public static final String REQUEST_PASSWORD_RESET_SUCCESS = "Request password reset successfully";
    public static final String RESET_PASSWORD_SUCCESS = "Reset password successfully";
    public static final String RESEND_PASSWORD_RESET_TOKEN_SUCCESS = "Resend password reset token successfully";
    public static final String CHANGE_PASSWORD_SUCCESS = "Change password successfully";

    //    Verification
    public static final String RESEND_EMAIL_VERIFICATION_TOKEN_SUCCESS = "Resend email verification token successfully";

    //    Cache
    public static final String CLEAR_CACHE_SUCCESS = "Clear cache successfully";
    public static final String GET_TIME_TO_LIVE_SUCCESS = "Get time to live successfully";

    private FeedbackMessage() {
        throw new AppException(ErrorCode.INITIATE_UTILITIES);
    }
}
