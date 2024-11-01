package com.personalproject.universal_pet_care.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity(name = "patients")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient extends User{
    @OneToMany(mappedBy = "sender")
    List<Appointment> appointments;
}
