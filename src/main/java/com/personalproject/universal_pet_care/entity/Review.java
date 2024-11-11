package com.personalproject.universal_pet_care.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

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
    long id;
    int stars;
    String feedback;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    User reviewer;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    User veterinarian;
    public void removeRelationship()
    {
        if(!Objects.isNull(reviewer)) this.setReviewer(null);
        if(!Objects.isNull(veterinarian)) this.setVeterinarian(null);
    }
}
