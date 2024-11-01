package com.personalproject.universal_pet_care.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity(name = "veterinarians")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Veterinarian extends User{
    String specialization;

    @OneToMany(mappedBy = "recipient")
    List<Appointment> appointments;
}
