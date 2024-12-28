package com.personalproject.universal_pet_care.service.token;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.entity.VerificationToken;
import com.personalproject.universal_pet_care.payload.request.VerificationTokenCreationRequest;

public interface VerificationTokenService {
    void checkValidToken(String token);
    void saveVerificationTokenForUser(VerificationTokenCreationRequest verificationTokenCreationRequest);
    void deleteToken(Long id);
    boolean isExpired(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
}
