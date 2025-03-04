package com.personalproject.universal_pet_care.payload.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdatingRequest {
    @NotBlank(message = "MISSING_FIELD")
    String firstName;
    String lastName;
    String gender;
    String phoneNumber;
    String specialization;
}
