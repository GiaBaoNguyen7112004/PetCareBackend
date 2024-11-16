package com.personalproject.universal_pet_care.repository;


import com.personalproject.universal_pet_care.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByReviewerIdAndVeterinarianId(long reviewerId, long veterinarianId);
    List<Review> findAllByVeterinarianId(long veterinarianId);

    @Query("SELECT r FROM reviews r WHERE r.reviewer.id = :userId OR r.veterinarian.id = :userId")
    Page<Review> findReviewsByUserId(@Param("userId") long userId, Pageable pageable);
}
