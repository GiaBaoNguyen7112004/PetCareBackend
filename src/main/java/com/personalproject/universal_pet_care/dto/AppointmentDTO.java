package com.personalproject.universal_pet_care.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO {
    Long id;
    String reason;

    @JsonFormat(pattern = "HH:mm")
    LocalTime appointmentTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate appointmentDate;

    String appointmentNo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    String status;

    UserDTO sender;
    UserDTO recipient;

    List<PetDTO> pets;
}
