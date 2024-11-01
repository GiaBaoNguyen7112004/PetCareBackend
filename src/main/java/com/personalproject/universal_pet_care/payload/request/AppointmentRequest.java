package com.personalproject.universal_pet_care.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRequest {
    String reason;

    @JsonFormat(pattern = "HH:mm")
    LocalTime appointmentTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate appointmentDate;

    long senderId;
    long recipientId;
}
