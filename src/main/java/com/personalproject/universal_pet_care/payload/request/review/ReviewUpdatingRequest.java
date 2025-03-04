package com.personalproject.universal_pet_care.payload.request.review;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewUpdatingRequest {
    @NotBlank(message = "MISSING_FIELD")
    int stars;
    String feedback;
}
