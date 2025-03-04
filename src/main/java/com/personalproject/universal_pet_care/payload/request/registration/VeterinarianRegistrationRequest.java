package com.personalproject.universal_pet_care.payload.request.registration;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VeterinarianRegistrationRequest extends RegistrationRequest {
    String specialization;
}
