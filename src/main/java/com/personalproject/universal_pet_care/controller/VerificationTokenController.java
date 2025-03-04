package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.token.VerificationTokenService;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.TOKEN_VERIFICATION)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationTokenController {
    VerificationTokenService verificationTokenService;

    @PostMapping(UrlMapping.VALIDATE_TOKEN)
    public ResponseEntity<ApiResponse> validateToken(@RequestParam String token) {
        verificationTokenService.validateToken(token);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.VALID_TOKEN)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(UrlMapping.CHECK_TOKEN_EXPIRATION)
    public ResponseEntity<ApiResponse> checkTokenExpiration(@RequestParam String token) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.VALID_TOKEN)
                .data(verificationTokenService.isExpired(token))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping(UrlMapping.DELETE_TOKEN)
    public ResponseEntity<ApiResponse> deleteToken(@PathVariable Long tokenId) {
        verificationTokenService.deleteToken(tokenId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.DELETE_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
