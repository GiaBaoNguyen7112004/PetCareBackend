package com.personalproject.universal_pet_care.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;
    String gender;
    String phoneNumber;

    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(100) COLLATE utf8mb4_unicode_ci")
    String email;

    String password;
    String userType;
    boolean isEnabled;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    Photo photo;
}
