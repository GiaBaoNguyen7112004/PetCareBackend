package com.personalproject.universal_pet_care.service.authentication;

import com.personalproject.universal_pet_care.dto.AuthenticationDTO;
import com.personalproject.universal_pet_care.payload.request.AuthenticationRequest;
import com.personalproject.universal_pet_care.payload.request.ChangePasswordRequest;

public interface AuthenticationService {
    AuthenticationDTO authenticate(AuthenticationRequest authenticationRequest);

    void resendPasswordResetToken(String email);

    void changePassword(ChangePasswordRequest changePasswordRequest);
}
