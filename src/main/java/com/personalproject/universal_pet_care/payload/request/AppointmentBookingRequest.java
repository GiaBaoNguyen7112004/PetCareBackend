package com.personalproject.universal_pet_care.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    List<PetRegistrationRequest> petRegistrationRequests;
}
