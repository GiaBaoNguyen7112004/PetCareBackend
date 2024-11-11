package com.personalproject.universal_pet_care.repository;

import com.personalproject.universal_pet_care.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByReviewerIdAndVeterinarianId(long reviewerId, long veterinarianId);
}
