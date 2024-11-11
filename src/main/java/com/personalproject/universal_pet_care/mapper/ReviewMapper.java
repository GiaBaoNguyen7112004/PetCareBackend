package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.ReviewDTO;
import com.personalproject.universal_pet_care.entity.Review;
import com.personalproject.universal_pet_care.payload.request.ReviewSubmissionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ReviewMapper {
    ReviewDTO toReviewDTO(Review review);
    Review toReview(ReviewSubmissionRequest reviewSubmissionRequest);
}
