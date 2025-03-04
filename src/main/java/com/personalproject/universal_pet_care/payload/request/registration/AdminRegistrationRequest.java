package com.personalproject.universal_pet_care.payload.request.registration;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminRegistrationRequest extends RegistrationRequest {
}
