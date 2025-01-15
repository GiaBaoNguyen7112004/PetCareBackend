package com.personalproject.universal_pet_care.service.appointment;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.payload.request.AppointmentBookingRequest;
import com.personalproject.universal_pet_care.payload.request.AppointmentUpdatingRequest;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO bookAppointment(AppointmentBookingRequest appointmentBookingRequest, long senderId, long recipientId);

    List<AppointmentDTO> getAllAppointments();

    AppointmentDTO updateAppointment(long id, AppointmentUpdatingRequest appointmentUpdatingRequest);

    void deleteAppointment(long id);

    AppointmentDTO getAppointmentById(long id);

    AppointmentDTO getAppointmentByNo(String no);

    List<AppointmentDTO> getAppointmentByUserId(Long id);

    AppointmentDTO cancelAppointment(Long id);

    AppointmentDTO approveAppointment(Long id);

    AppointmentDTO declineAppointment(Long id);
}
