package com.personalproject.universal_pet_care.payload.request.authentication;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = "MISSING_FIELD")
    @Email(message = "INVALID_EMAIL")
    String email;

    @NotBlank(message = "MISSING_FIELD")
    String password;
}
