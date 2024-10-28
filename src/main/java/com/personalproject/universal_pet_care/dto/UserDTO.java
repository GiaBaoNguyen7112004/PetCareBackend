package com.personalproject.universal_pet_care.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    long id;
    String firstName;
    String lastName;
    String gender;
    String phoneNumber;
    String email;
    String userType;
    boolean isEnabled;
}
