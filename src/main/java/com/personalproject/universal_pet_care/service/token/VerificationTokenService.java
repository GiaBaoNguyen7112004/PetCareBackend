package com.personalproject.universal_pet_care.service.token;

import com.personalproject.universal_pet_care.entity.User;


public interface VerificationTokenService {
    void validateToken(String token);

    String saveVerificationTokenForUser(User user, String token);

    void deleteToken(Long id);

    boolean isExpired(String token);

    String recreateNewVerificationToken(String oldToken);

    void resendVerificationEmailToken(String email);
}
