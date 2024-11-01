package com.personalproject.universal_pet_care.service.appointment;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.AppointmentMapper;
import com.personalproject.universal_pet_care.payload.request.AppointmentRequest;
import com.personalproject.universal_pet_care.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService{
    AppointmentMapper appointmentMapper;
    UserRepository userRepository;

    @Override
    public AppointmentDTO createAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = appointmentMapper.toAppointment(appointmentRequest);

        appointment.addSender(userRepository.findById(appointmentRequest.getSenderId())
                .orElseThrow(()-> new AppException(ErrorCode.NO_DATA_FOUND)));
        appointment.addRecipient(userRepository.findById(appointmentRequest.getRecipientId())
                .orElseThrow(()-> new AppException(ErrorCode.NO_DATA_FOUND)));
        appointment.setAppointmentNo();
        appointment.setStatus(AppointmentStatus.WAITING_FOR_APPROVAL);

        return appointmentMapper.toAppointmentDTO(appointment);
    }
}
