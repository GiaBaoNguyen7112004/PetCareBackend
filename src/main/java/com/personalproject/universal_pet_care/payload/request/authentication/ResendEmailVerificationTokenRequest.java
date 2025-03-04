package com.personalproject.universal_pet_care.payload.request.authentication;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResendEmailVerificationTokenRequest {
    String email;
}
