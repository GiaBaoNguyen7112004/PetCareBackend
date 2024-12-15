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
import java.util.Date;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUtils {
    @Value("${jwt.secretKey}")
    String secretKey;

    @Value("${jwt.expirationTimeInMs}")
    long expirationTimeInMs;

    public String generateToken(Authentication authentication) {
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

        List<String> roles = appUserDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder().setSubject(appUserDetails.getUsername())
                .claim("id", appUserDetails.getUser().getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTimeInMs))
                .signWith(getKey(), SignatureAlgorithm.HS512).compact();
    }

    private Key getKey()
    {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

//    Dung parserBuilder() de tao ra 1 doi tuong JwtParser dung de phan tich va giai ma token
    public String getUsernameFromToken(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean verifyToken(String token)
    {
        try{
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e)
        {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
