package com.personalproject.universal_pet_care.service.review;

import com.personalproject.universal_pet_care.dto.ReviewDTO;
import com.personalproject.universal_pet_care.payload.request.ReviewSubmissionRequest;
import com.personalproject.universal_pet_care.payload.request.ReviewUpdatingRequest;

import java.util.List;

public interface ReviewService {
    ReviewDTO submitReview(ReviewSubmissionRequest reviewSubmissionRequest, long reviewerId, long veterinarianId);
    Double getAverageStarForVet(Long veterinarianId);

    ReviewDTO updateReview(long id, ReviewUpdatingRequest reviewUpdatingRequest);

    List<ReviewDTO> getReviewsByUserId(Long userId, int pageNumber, int pageSize);

    void deleteReview(long id);

    List<ReviewDTO> getAllReviewsOfUser(Long userId);
}
