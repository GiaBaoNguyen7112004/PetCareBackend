package com.personalproject.universal_pet_care.service.authentication;

import com.personalproject.universal_pet_care.dto.AuthenticationDTO;
import com.personalproject.universal_pet_care.payload.request.AuthenticationRequest;
import com.personalproject.universal_pet_care.security.jwt.JwtUtils;
import com.personalproject.universal_pet_care.security.user.AppUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    JwtUtils jwtUtils;
    AuthenticationManager authenticationManager;

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


}
