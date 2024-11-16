package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.ReviewDTO;
import com.personalproject.universal_pet_care.entity.Review;

import com.personalproject.universal_pet_care.payload.request.ReviewUpdatingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ReviewMapper {
    @Mapping(target = "reviewer", source = "reviewer")
    ReviewDTO toReviewDTO(Review review);
    void updateReview(@MappingTarget Review review, ReviewUpdatingRequest reviewUpdatingRequest);
}
