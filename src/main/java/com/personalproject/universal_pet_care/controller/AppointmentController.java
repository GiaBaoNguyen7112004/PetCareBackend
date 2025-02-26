package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.request.AppointmentBookingRequest;
import com.personalproject.universal_pet_care.payload.request.AppointmentUpdatingRequest;
import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.appointment.AppointmentService;
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
    AppointmentService appointmentService;

    @PostMapping(UrlMapping.BOOK_APPOINTMENT)
    public ResponseEntity<ApiResponse> bookAppointment(@RequestBody AppointmentBookingRequest appointmentBookingRequest,
                                                       @RequestParam long senderId, @RequestParam long recipientId) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.bookAppointment(appointmentBookingRequest, senderId, recipientId))
                .message(FeedbackMessage.CREATE_SUCCESS)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_ALL_APPOINTMENTS)
    public ResponseEntity<ApiResponse> getAllAppointments() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.getAllAppointments())
                .message(FeedbackMessage.GET_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(UrlMapping.UPDATE_APPOINTMENT)
    public ResponseEntity<ApiResponse> updateAppointment(@PathVariable long id,
                                                         @RequestBody AppointmentUpdatingRequest appointmentUpdatingRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.updateAppointment(id, appointmentUpdatingRequest))
                .message(FeedbackMessage.UPDATE_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping(UrlMapping.DELETE_APPOINTMENT)
    public ResponseEntity<ApiResponse> deleteAppointment(@PathVariable long id) {
        appointmentService.deleteAppointment(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.DELETE_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_ID)
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.getAppointmentById(id))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_NO)
    public ResponseEntity<ApiResponse> getAppointmentByNo(@PathVariable String no) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.getAppointmentByNo(no))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(UrlMapping.CANCEL_APPOINTMENT)
    public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.cancelAppointment(id))
                .message(FeedbackMessage.CANCEL_APPOINTMENT_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(UrlMapping.APPROVE_APPOINTMENT)
    public ResponseEntity<ApiResponse> approveAppointment(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.approveAppointment(id))
                .message(FeedbackMessage.APPROVE_APPOINTMENT_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(UrlMapping.DECLINE_APPOINTMENT)
    public ResponseEntity<ApiResponse> declineAppointment(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.declineAppointment(id))
                .message(FeedbackMessage.DECLINE_APPOINTMENT_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(UrlMapping.CONFIRM_COMPLETE_APPOINTMENT)
    public ResponseEntity<ApiResponse> confirmCompleteAppointment(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(appointmentService.confirmCompleteAppointment(id))
                .message(FeedbackMessage.APPOINTMENT_COMPLETED)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
