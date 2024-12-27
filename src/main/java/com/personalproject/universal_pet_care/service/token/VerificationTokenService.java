package com.personalproject.universal_pet_care.service.token;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.entity.VerificationToken;

public interface VerificationTokenService {
    String validateToken(String token);
    void saveVerificationTokenForUser(String token, User user);
    void deleteToken(Long id);
    boolean isExpired(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
}
