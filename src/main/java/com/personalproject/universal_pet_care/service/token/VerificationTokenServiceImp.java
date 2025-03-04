package com.personalproject.universal_pet_care.service.token;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.entity.VerificationToken;

import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;

import com.personalproject.universal_pet_care.repository.VerificationTokenRepository;
import com.personalproject.universal_pet_care.repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VerificationTokenServiceImp implements VerificationTokenService {
    VerificationTokenRepository verificationTokenRepository;
    UserRepository userRepository;

    @Override
    public void validateToken(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_VERIFICATION_TOKEN);
        }

        User user = verificationToken.get().getUser();
        if (user.isEnabled()) throw new AppException(ErrorCode.ALREADY_VERIFIED_ACCOUNT);
        if (isExpired(token)) throw new AppException(ErrorCode.EXPIRED_TOKEN);

        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken.get());
    }

    @Override
    public String saveVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        return verificationTokenRepository.save(verificationToken).getToken();
    }

    @Override
    public void deleteToken(Long id) {
        verificationTokenRepository.deleteById(id);
    }

    @Override
    public boolean isExpired(String token) {
        return verificationTokenRepository.findByToken(token)
                .map(value -> value.getExpiryTime().isBefore(LocalDateTime.now()))
                .orElse(true);
    }

    @Override
    public String recreateNewVerificationToken(String oldToken) {
        return verificationTokenRepository.findByToken(oldToken).map(
                verificationToken -> {
                    verificationToken.setToken(UUID.randomUUID().toString());
                    verificationToken.setExpiryTime(LocalDateTime.now());
                    return verificationTokenRepository.save(verificationToken).getToken();
                }
        ).orElseThrow(() -> new AppException(ErrorCode.INVALID_VERIFICATION_TOKEN));
    }
}
