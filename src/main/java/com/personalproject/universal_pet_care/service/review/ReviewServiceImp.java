package com.personalproject.universal_pet_care.service.review;

import com.personalproject.universal_pet_care.dto.ReviewDTO;
import com.personalproject.universal_pet_care.entity.Review;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.ReviewMapper;
import com.personalproject.universal_pet_care.payload.request.ReviewSubmissionRequest;
import com.personalproject.universal_pet_care.payload.request.ReviewUpdatingRequest;
import com.personalproject.universal_pet_care.repository.AppointmentRepository;
import com.personalproject.universal_pet_care.repository.ReviewRepository;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService {
    ReviewRepository reviewRepository;
    AppointmentRepository appointmentRepository;
    ReviewMapper reviewMapper;
    UserRepository userRepository;

    @Override
    public ReviewDTO submitReview(ReviewSubmissionRequest reviewSubmissionRequest, long reviewerId, long veterinarianId)
    {
        if(reviewerId == veterinarianId) throw new AppException(ErrorCode.REVIEW_YOURSELF);

        if(reviewRepository.existsByReviewerIdAndVeterinarianId(reviewerId, veterinarianId))
            throw new AppException(ErrorCode.ALREADY_REVIEWED);

        if(!appointmentRepository.existsBySenderIdAndRecipientIdAndStatus(reviewerId, veterinarianId,
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

    @Override
    public Double getAverageStarForVet(Long veterinarianId)
    {
        return reviewRepository.findAllByVeterinarianId(veterinarianId).stream().mapToInt(Review::getStars)
                .average().orElse(0);
    }

    @Override
    public ReviewDTO updateReview(long id, ReviewUpdatingRequest reviewUpdatingRequest)
    {
        return reviewRepository.findById(id).map(review -> {
                    reviewMapper.updateReview(review, reviewUpdatingRequest);
                    return reviewMapper.toReviewDTO(reviewRepository.save(review));
                }
        ).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId, int pageNumber, int pageSize)
    {
        return reviewRepository.findReviewsByUserId(userId, PageRequest.of(pageNumber, pageSize)).getContent().stream()
                .map(reviewMapper::toReviewDTO).toList();
    }

    @Override
    public void deleteReview(long id)
    {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewDTO> getAllReviewsOfUser(Long userId)
    {
        return reviewRepository.findReviewsByUserId(userId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .map(reviewMapper::toReviewDTO).toList();
    }
}
