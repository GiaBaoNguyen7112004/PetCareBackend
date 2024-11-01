package com.personalproject.universal_pet_care.service.appointment;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.payload.request.AppointmentRequest;

public interface IAppointmentService {
    AppointmentDTO createAppointment(AppointmentRequest appointmentRequest);
}
