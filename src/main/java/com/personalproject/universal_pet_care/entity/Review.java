package com.personalproject.universal_pet_care.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int stars;
    String feedback;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    User reviewer;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    User veterinarian;
}
