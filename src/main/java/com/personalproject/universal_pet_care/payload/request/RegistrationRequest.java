package com.personalproject.universal_pet_care.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {
    String firstName;
    String lastName;
    String gender;
    String phoneNumber;
    String email;
    String password;
    String userType;

    boolean isEnabled;
    String specialization;
}
