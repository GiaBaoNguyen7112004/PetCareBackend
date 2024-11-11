package com.personalproject.universal_pet_care.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatingRequest {
    String firstName;
    String lastName;
    String gender;
    String phoneNumber;
    String specialization;
}
