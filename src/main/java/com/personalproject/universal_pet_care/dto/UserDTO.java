package com.personalproject.universal_pet_care.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    long id;
    String firstName;
    String lastName;
    String gender;
    String phoneNumber;
    String email;
    String userType;
    boolean isEnabled;
    String specialization;
}
