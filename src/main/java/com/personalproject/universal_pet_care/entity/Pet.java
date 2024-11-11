package com.personalproject.universal_pet_care.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity(name = "pets")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String type;
    String color;
    String breed;
    int age;

    @JoinColumn(name = "appointment_id")
    @ManyToOne
    Appointment appointment;
}
