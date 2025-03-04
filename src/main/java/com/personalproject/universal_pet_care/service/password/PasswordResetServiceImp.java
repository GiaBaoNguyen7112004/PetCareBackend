package com.personalproject.universal_pet_care.service.password;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.entity.VerificationToken;
import com.personalproject.universal_pet_care.event.PasswordResetEvent;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.payload.request.authentication.PasswordResetConfirmRequest;
import com.personalproject.universal_pet_care.payload.request.authentication.PasswordResetEmailRequest;

import com.personalproject.universal_pet_care.repository.VerificationTokenRepository;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PasswordResetServiceImp implements PasswordResetService {
    UserRepository userRepository;
    ApplicationEventPublisher applicationEventPublisher;
    VerificationTokenRepository verificationTokenRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public void requestResetPassword(PasswordResetEmailRequest passwordResetEmailRequest) {
        User user = userRepository.findByEmail(passwordResetEmailRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        applicationEventPublisher.publishEvent(new PasswordResetEvent(user));
    }

    @Override
    public void resetPassword(PasswordResetConfirmRequest passwordResetConfirmRequest) {
        if (Objects.isNull(passwordResetConfirmRequest.getNewPassword()))
            throw new AppException(ErrorCode.MISSING_PASSWORD);

        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(passwordResetConfirmRequest.getVerificationToken())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_VERIFICATION_TOKEN));
        User user = verificationToken.getUser();

        if (verificationToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            verificationTokenRepository.delete(verificationToken);
            throw new AppException(ErrorCode.EXPIRED_TOKEN);
        }

        verificationTokenRepository.delete(verificationToken);
        user.setPassword(passwordEncoder.encode(passwordResetConfirmRequest.getNewPassword()));
        userRepository.save(user);
    }
}
