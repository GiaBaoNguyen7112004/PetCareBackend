package com.personalproject.universal_pet_care.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    long id;
    String firstName;
    String lastName;
    String gender;
    String phoneNumber;
    String email;
    String userType;
    boolean isEnabled;
    String specialization;
    PhotoDTO photo;
    Double averageStars;
    List<ReviewDTO> reviewDTOs;
    List<AppointmentDTO> appointmentDTOs;
}
