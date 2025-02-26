package com.personalproject.universal_pet_care.service.password;


import com.personalproject.universal_pet_care.payload.request.PasswordResetConfirmRequest;
import com.personalproject.universal_pet_care.payload.request.PasswordResetEmailRequest;

public interface PasswordResetService {

    void requestResetPassword(PasswordResetEmailRequest passwordResetEmailRequest);

    void resetPassword(PasswordResetConfirmRequest passwordResetConfirmRequest);
}
