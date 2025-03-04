package com.personalproject.universal_pet_care.payload.request.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personalproject.universal_pet_care.payload.request.pet.PetRegistrationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentBookingRequest {
    String reason;

    @JsonFormat(pattern = "HH:mm")
    LocalTime appointmentTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate appointmentDate;

    @NotBlank(message = "MISSING_FIELD")
    List<PetRegistrationRequest> petRegistrationRequests;
}
