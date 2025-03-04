package com.personalproject.universal_pet_care.payload.request.authentication;

import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetEmailRequest {
    @Email(message = "INVALID_EMAIL")
    String email;
}
