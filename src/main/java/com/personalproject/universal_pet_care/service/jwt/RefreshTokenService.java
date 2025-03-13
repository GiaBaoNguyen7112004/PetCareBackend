package com.personalproject.universal_pet_care.service.jwt;

import com.personalproject.universal_pet_care.security.jwt.JwtUtils;
import com.personalproject.universal_pet_care.service.cache.CacheService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenService {
    CacheService cacheService;
    JwtUtils jwtUtils;

    static final String REFRESH_TOKEN_PREFIX = "refresh_token";

    public void saveRefreshTokenForUser(String refreshToken, long userId) {
        long durationExpirationTime = jwtUtils.getDurationValidTime(refreshToken);
        cacheService.set(REFRESH_TOKEN_PREFIX + userId, refreshToken, durationExpirationTime, TimeUnit.SECONDS);
    }

    public boolean isValidRefreshToken(String refreshToken, long userId) {
        String inRedisRefreshToken = (String) cacheService.get(REFRESH_TOKEN_PREFIX + userId);
        return inRedisRefreshToken != null && inRedisRefreshToken.equals(refreshToken);
    }
}
