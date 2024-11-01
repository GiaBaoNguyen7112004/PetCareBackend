package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.request.AppointmentRequest;
import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.appointment.IAppointmentService;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.APPOINTMENTS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppointmentController {
    IAppointmentService iAppointmentService;

    @PostMapping(UrlMapping.BOOK_APPOINTMENT)
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest appointmentRequest,
                                               @RequestParam long senderId, @RequestParam long recipientId)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iAppointmentService.createAppointment(appointmentRequest, senderId, recipientId))
                .message(FeedbackMessage.CREATE_SUCCESS)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
