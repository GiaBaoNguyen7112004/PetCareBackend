package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.request.authentication.*;
import com.personalproject.universal_pet_care.payload.response.ApiResponse;

import com.personalproject.universal_pet_care.service.authentication.AuthenticationService;
import com.personalproject.universal_pet_care.service.password.PasswordResetService;
import com.personalproject.universal_pet_care.service.token.VerificationTokenService;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.AUTHENTICATION)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationController {
    AuthenticationService authenticationService;
    VerificationTokenService verificationTokenService;
    PasswordResetService passwordResetService;

    @PostMapping(UrlMapping.LOGIN)
    public ResponseEntity<ApiResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.AUTHENTICATE_SUCCESS)
                .data(authenticationService.authenticate(authenticationRequest))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(UrlMapping.VERIFY_EMAIL)
    public ResponseEntity<ApiResponse> verifyEmail(@RequestParam String token) {
        verificationTokenService.validateToken(token);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.VALID_TOKEN)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(UrlMapping.REQUEST_PASSWORD_RESET)
    public ResponseEntity<ApiResponse> requestPasswordReset(@RequestBody @Valid
                                                            PasswordResetEmailRequest passwordResetEmailRequest) {
        passwordResetService.requestResetPassword(passwordResetEmailRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.REQUEST_PASSWORD_RESET_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(UrlMapping.RESET_PASSWORD)
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid
                                                     PasswordResetConfirmRequest passwordResetConfirmRequest) {
        passwordResetService.resetPassword(passwordResetConfirmRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.RESET_PASSWORD_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(UrlMapping.RESEND_PASSWORD_RESET_TOKEN)
    public ResponseEntity<ApiResponse> resendPasswordResetToken(@RequestBody @Valid
                                                                PasswordResetEmailRequest passwordResetEmailRequest) {
        authenticationService.resendPasswordResetToken(passwordResetEmailRequest.getEmail());
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.RESEND_PASSWORD_RESET_TOKEN_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(UrlMapping.CHANGE_PASSWORD)
    public ResponseEntity<ApiResponse> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        authenticationService.changePassword(changePasswordRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.CHANGE_PASSWORD_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(UrlMapping.RESEND_EMAIL_VERIFICATION_TOKEN)
    public ResponseEntity<ApiResponse>
    resendEmailVerificationToken(@RequestBody ResendEmailVerificationTokenRequest resendEmailVerificationTokenRequest) {
        authenticationService.resendVerificationEmailToken(resendEmailVerificationTokenRequest.getEmail());
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.RESEND_EMAIL_VERIFICATION_TOKEN_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
