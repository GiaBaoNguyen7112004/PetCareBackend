package com.personalproject.universal_pet_care.security.jwt;

import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.security.user.AppUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUtils {
    @Value("${jwt.secretKey}")
    String secretKey;

    @Value("${jwt.valid-time-duration}")
    long validTimeDuration;

    @Value("${jwt.refresh-time-duration}")
    long refreshTimeDuration;

    static final String ID_CLAIM = "id";
    static final String ROLES_CLAIM = "roles";

    public String generateToken(Authentication authentication, boolean isRefreshToken) {
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

        List<String> roles = appUserDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder().setSubject(appUserDetails.getUsername())
                .claim(ID_CLAIM, appUserDetails.getUser().getId())
                .claim(ROLES_CLAIM, roles)
                .setIssuedAt(new Date())
                .setExpiration(isRefreshToken
                        ? new Date(new Date().getTime() + refreshTimeDuration)
                        : new Date(new Date().getTime() + validTimeDuration))
                .signWith(getKey(), SignatureAlgorithm.HS512).compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    //    Dung parserBuilder()...build() de tao ra 1 doi tuong JwtParser dung de phan tich va giai ma token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getExpiration();
    }

    public long getDurationValidTime(String token) {
        Date now = new Date();
        Date expirationDate = getExpirationDateFromToken(token);

        Instant oldInstant = expirationDate.toInstant();
        Instant nowInstant = now.toInstant();

        return Duration.between(oldInstant, nowInstant).getSeconds();
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
