package com.personalproject.universal_pet_care.service.appointment;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.payload.request.BookAppointmentRequest;
import com.personalproject.universal_pet_care.payload.request.UpdateAppointmentRequest;

import java.util.List;

public interface IAppointmentService {
    AppointmentDTO createAppointment(BookAppointmentRequest bookAppointmentRequest, long senderId, long recipientId);

    List<AppointmentDTO> getAllAppointments();

    AppointmentDTO updateAppointment(long id, UpdateAppointmentRequest updateAppointmentRequest);

    void deleteAppointment(long id);

    AppointmentDTO getAppointmentById(long id);

    AppointmentDTO getAppointmentByNo(String no);
}
