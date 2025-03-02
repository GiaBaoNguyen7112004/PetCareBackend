package com.personalproject.universal_pet_care.service.authentication;

import com.personalproject.universal_pet_care.dto.AuthenticationDTO;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.event.PasswordResetEvent;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.payload.request.AuthenticationRequest;

import com.personalproject.universal_pet_care.payload.request.ChangePasswordRequest;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import com.personalproject.universal_pet_care.security.jwt.JwtUtils;
import com.personalproject.universal_pet_care.security.user.AppUserDetails;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImp implements AuthenticationService {
    JwtUtils jwtUtils;
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    ApplicationEventPublisher applicationEventPublisher;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationDTO authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

        return AuthenticationDTO.builder()
                .id(appUserDetails.getUser().getId())
                .token(jwtUtils.generateToken(authentication))
                .build();
    }

    @Override
    public void resendPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        applicationEventPublisher.publishEvent(new PasswordResetEvent(user));
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
        } else throw new AppException(ErrorCode.INCORRECT_OLD_PASSWORD);
    }
}
