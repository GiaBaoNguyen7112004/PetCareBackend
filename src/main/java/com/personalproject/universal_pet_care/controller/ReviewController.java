package com.personalproject.universal_pet_care.controller;

import com.personalproject.universal_pet_care.payload.request.review.ReviewSubmissionRequest;
import com.personalproject.universal_pet_care.payload.request.review.ReviewUpdatingRequest;
import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.review.ReviewService;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.REVIEWS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {
    ReviewService reviewService;

    @PostMapping(UrlMapping.SUBMIT_REVIEW)
    public ResponseEntity<ApiResponse> submitReview(@RequestBody @Valid ReviewSubmissionRequest reviewSubmissionRequest,
                                                    @PathVariable long reviewerId, @PathVariable long veterinarianId) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(reviewService.submitReview(reviewSubmissionRequest, reviewerId, veterinarianId))
                .message(FeedbackMessage.CREATE_SUCCESS)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping(UrlMapping.UPDATE_REVIEW)
    public ResponseEntity<ApiResponse> updateReview(@PathVariable long id,
                                                    @RequestBody @Valid ReviewUpdatingRequest reviewUpdatingRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(reviewService.updateReview(id, reviewUpdatingRequest))
                .message(FeedbackMessage.UPDATE_SUCCESS)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping(UrlMapping.DELETE_REVIEW)
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable long id) {
        reviewService.deleteReview(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.DELETE_SUCCESS)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_AVERAGE_STARS_FOR_VET)
    public ResponseEntity<ApiResponse> getAvgStarsForVet(@PathVariable long veterinarianId) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(reviewService.getAverageStarForVet(veterinarianId))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_REVIEWS_BY_ID)
    public ResponseEntity<ApiResponse> getReviewsByUserId(@PathVariable long id, @RequestParam int pageNumber,
                                                          @RequestParam int pageSize) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(reviewService.getReviewsByUserId(id, pageNumber, pageSize))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
