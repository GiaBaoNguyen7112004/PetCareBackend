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

    public static final String CANCEL_APPOINTMENT_SUCCESS = "Cancel appointment successfully";
    public static final String APPROVE_APPOINTMENT_SUCCESS = "Approve appointment successfully";
    public static final String DECLINE_APPOINTMENT_SUCCESS = "Decline appointment successfully";


    public static final String AUTHENTICATE_SUCCESS = "Authenticated successfully";
    public static final String VALID_TOKEN = "Token is valid";

    private FeedbackMessage() {
        throw new AppException(ErrorCode.INITIATE_UTILITIES);
    }
}
