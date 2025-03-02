package com.personalproject.universal_pet_care.payload.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
    @NotBlank(message = "MISSING_FIELD")
    String oldPassword;
    @NotBlank(message = "MISSING_FIELD")
    String newPassword;
}
