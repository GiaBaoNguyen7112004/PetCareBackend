package com.personalproject.universal_pet_care.service.token;

import com.personalproject.universal_pet_care.entity.VerificationToken;
import com.personalproject.universal_pet_care.payload.request.VerificationTokenCreationRequest;

public interface VerificationTokenService {
    void validateToken(String token);

    void saveVerificationTokenForUser(VerificationTokenCreationRequest verificationTokenCreationRequest);

    void deleteToken(Long id);

    boolean isExpired(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
