package com.personalproject.universal_pet_care.service.password;


import com.personalproject.universal_pet_care.payload.request.authentication.PasswordResetConfirmRequest;
import com.personalproject.universal_pet_care.payload.request.authentication.PasswordResetEmailRequest;

public interface PasswordResetService {

    void requestResetPassword(PasswordResetEmailRequest passwordResetEmailRequest);

    void resetPassword(PasswordResetConfirmRequest passwordResetConfirmRequest);
}
