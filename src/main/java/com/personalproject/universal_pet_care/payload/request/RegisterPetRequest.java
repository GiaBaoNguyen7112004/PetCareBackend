package com.personalproject.universal_pet_care.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterPetRequest {
    String name;
    String type;
    String color;
    String breed;
    int age;
}
