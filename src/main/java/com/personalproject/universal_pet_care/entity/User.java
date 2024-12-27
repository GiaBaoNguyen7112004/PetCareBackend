package com.personalproject.universal_pet_care.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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

    @ManyToMany
    @JoinTable(name ="user_role",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    List<VerificationToken> verificationTokens;

    public Collection<Role> getRoles() {
        if(Objects.isNull(roles)) roles = new HashSet<>();
        return roles;
    }
}
