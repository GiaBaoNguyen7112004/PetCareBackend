package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.request.AppointmentRequest;
import com.personalproject.universal_pet_care.payload.request.UpdateAppointmentRequest;
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
    public ResponseEntity<ApiResponse> createAppointment(@RequestBody AppointmentRequest appointmentRequest,
                                               @RequestParam long senderId, @RequestParam long recipientId)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iAppointmentService.createAppointment(appointmentRequest, senderId, recipientId))
                .message(FeedbackMessage.CREATE_SUCCESS)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_ALL_APPOINTMENTS)
    public ResponseEntity<ApiResponse> getAllAppointments()
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iAppointmentService.getAllAppointments())
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(UrlMapping.UPDATE_APPOINTMENT)
    public ResponseEntity<ApiResponse> updateAppointment(@PathVariable long id,
                                               @RequestBody UpdateAppointmentRequest updateAppointmentRequest)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iAppointmentService.updateAppointment(id, updateAppointmentRequest))
                .message(FeedbackMessage.UPDATE_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping(UrlMapping.DELETE_APPOINTMENT)
    public ResponseEntity<ApiResponse> deleteAppointment(@PathVariable long id)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iAppointmentService.deleteAppointment(id))
                .message(FeedbackMessage.DELETE_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_ID)
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable long id)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iAppointmentService.getAppointmentById(id))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_NO)
    public ResponseEntity<ApiResponse> getAppointmentByNo(@PathVariable String no)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iAppointmentService.getAppointmentByNo(no))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }
}
