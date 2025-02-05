package com.personalproject.universal_pet_care.controller;

import com.personalproject.universal_pet_care.dto.AuthenticationDTO;
import com.personalproject.universal_pet_care.payload.request.AuthenticationRequest;
import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.security.jwt.JwtUtils;
import com.personalproject.universal_pet_care.security.user.AppUserDetails;
import com.personalproject.universal_pet_care.service.token.VerificationTokenService;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.AUTHENTICATION)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    VerificationTokenService verificationTokenService;

    @PostMapping(UrlMapping.LOGIN)
    public ResponseEntity<ApiResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

        AuthenticationDTO authenticationDTO = AuthenticationDTO.builder()
                .id(appUserDetails.getUser().getId())
                .token(jwtUtils.generateToken(authentication))
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.AUTHENTICATE_SUCCESS)
                .data(authenticationDTO)
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
}
