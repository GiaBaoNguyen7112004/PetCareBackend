package com.personalproject.universal_pet_care.service.review;

import com.personalproject.universal_pet_care.dto.ReviewDTO;
import com.personalproject.universal_pet_care.payload.request.ReviewSubmissionRequest;

public interface IReviewService{
    ReviewDTO createReview(ReviewSubmissionRequest reviewSubmissionRequest, long reviewerId, long veterinarianId);
}
