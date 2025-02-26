package com.personalproject.universal_pet_care.service.token;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.entity.VerificationToken;


public interface VerificationTokenService {
    void validateToken(String token);

    void saveVerificationTokenForUser(User user, String token);

    void deleteToken(Long id);

    boolean isExpired(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
