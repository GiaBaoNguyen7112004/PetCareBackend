package com.personalproject.universal_pet_care.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public enum UserType {
    ADMIN,
    VETERINARIAN,
    PATIENT,
}
