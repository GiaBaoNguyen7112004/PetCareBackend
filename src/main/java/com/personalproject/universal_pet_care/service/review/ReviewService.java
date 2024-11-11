package com.personalproject.universal_pet_care.service.review;

import com.personalproject.universal_pet_care.dto.ReviewDTO;
import com.personalproject.universal_pet_care.entity.Review;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.ReviewMapper;
import com.personalproject.universal_pet_care.payload.request.ReviewSubmissionRequest;
import com.personalproject.universal_pet_care.repository.AppointmentRepository;
import com.personalproject.universal_pet_care.repository.ReviewRepository;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewService implements IReviewService{
    ReviewRepository reviewRepository;
    AppointmentRepository appointmentRepository;
    ReviewMapper reviewMapper;
    UserRepository userRepository;

    @Override
    public ReviewDTO createReview(ReviewSubmissionRequest reviewSubmissionRequest, long reviewerId, long veterinarianId)
    {
        if(reviewerId == veterinarianId) throw new AppException(ErrorCode.REVIEW_YOURSELF);

        if(reviewRepository.existsByReviewerIdAndVeterinarianId(reviewerId, veterinarianId))
            throw new AppException(ErrorCode.ALREADY_REVIEWED);

        if(appointmentRepository.existsBySenderIdAndRecipientIdAndStatus(reviewerId, veterinarianId,
                AppointmentStatus.COMPLETED))
            throw new AppException(ErrorCode.REVIEW_NOT_COMPLETED);

        Review review = Review.builder()
                .stars(reviewSubmissionRequest.getStars())
                .feedback(reviewSubmissionRequest.getFeedback())
                .reviewer(userRepository.findById(reviewerId).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)))
                .veterinarian(userRepository.findById(veterinarianId).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)))
                .build();

        return reviewMapper.toReviewDTO(reviewRepository.save(review));
    }
}
