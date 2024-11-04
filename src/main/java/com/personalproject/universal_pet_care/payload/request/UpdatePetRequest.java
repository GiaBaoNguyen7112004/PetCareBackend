package com.personalproject.universal_pet_care.payload.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePetRequest {
    String name;
    String type;
    String color;
    String breed;
    int age;
}
