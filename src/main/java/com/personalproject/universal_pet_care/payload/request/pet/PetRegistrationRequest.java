package com.personalproject.universal_pet_care.payload.request.pet;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetRegistrationRequest {
    String name;
    @NotBlank(message = "MISSING_FIELD")
    String type;
    String color;
    String breed;
    int age;
}
