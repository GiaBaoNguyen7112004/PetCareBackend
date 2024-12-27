package com.personalproject.universal_pet_care.utils;

import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;

import java.time.LocalDateTime;

public class SystemUtils {
    private static final long EXPIRATION_TIME = 2;

    private SystemUtils() {
        throw new AppException(ErrorCode.INITIATE_UTILITIES);
    }

    public static LocalDateTime getExpirationTime() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
    }
}
