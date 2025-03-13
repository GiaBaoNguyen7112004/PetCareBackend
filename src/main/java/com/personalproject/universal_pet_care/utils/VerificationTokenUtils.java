package com.personalproject.universal_pet_care.utils;

import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;

import java.time.LocalDateTime;

public class VerificationTokenUtils {
    private static final long EXPIRATION_TIME = 2;

    private VerificationTokenUtils() {
        throw new AppException(ErrorCode.INITIATE_UTILITIES);
    }

    public static LocalDateTime getExpirationTime() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
    }
}
