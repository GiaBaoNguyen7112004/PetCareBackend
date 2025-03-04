package com.personalproject.universal_pet_care.payload.request.appointment;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentUpdatingRequest {
    String reason;
    LocalTime appointmentTime;
    LocalDate appointmentDate;
}
