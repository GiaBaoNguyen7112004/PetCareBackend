package com.personalproject.universal_pet_care.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationTokenCreationRequest {
    String token;
    Long userId;
}
