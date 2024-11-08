package com.personalproject.universal_pet_care.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Entity(name = "photos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String fileName;
    String fileType;

    @Lob
    byte[] image;
}
