package com.personalproject.universal_pet_care.payload.request.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetConfirmRequest {
    String verificationToken;

    @NotBlank(message = "MISSING_FIELD")
    @Size(min = 6, max = 20, message = "INVALID_PASSWORD_LENGTH")
    @Pattern(regexp = "^[a-zA-Z0-9@#$%^&*]+$", message = "INVALID_PASSWORD_CONTENT")
    String newPassword;
}
