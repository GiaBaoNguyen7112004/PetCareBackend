package com.personalproject.universal_pet_care.service.authentication;

import com.personalproject.universal_pet_care.dto.AuthenticationDTO;
import com.personalproject.universal_pet_care.dto.IntrospectDTO;
import com.personalproject.universal_pet_care.payload.request.authentication.AuthenticationRequest;
import com.personalproject.universal_pet_care.payload.request.authentication.ChangePasswordRequest;
import com.personalproject.universal_pet_care.payload.request.authentication.IntrospectRequest;

public interface AuthenticationService {
    IntrospectDTO introspect(IntrospectRequest introspectRequest);

    AuthenticationDTO authenticate(AuthenticationRequest authenticationRequest);

    void resendPasswordResetToken(String email);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void resendVerificationEmailToken(String email);
}
