package com.personalproject.universal_pet_care.entity;

import com.personalproject.universal_pet_care.utils.VerificationTokenUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "verification_tokens")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String token;
    LocalDateTime expiryTime;

    @OneToOne
    User user;

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expiryTime = VerificationTokenUtils.getExpirationTime();
    }
}
