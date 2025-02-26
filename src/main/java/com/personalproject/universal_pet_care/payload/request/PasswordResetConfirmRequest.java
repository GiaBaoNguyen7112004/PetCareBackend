package com.personalproject.universal_pet_care.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetConfirmRequest {
    String verificationToken;
    String newPassword;
}
