package com.personalproject.universal_pet_care.service.appointment;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.AppointmentMapper;
import com.personalproject.universal_pet_care.payload.request.AppointmentRequest;
import com.personalproject.universal_pet_care.payload.request.UpdateAppointmentRequest;
import com.personalproject.universal_pet_care.repository.appointment.AppointmentRepository;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    AppointmentMapper appointmentMapper;
    UserRepository userRepository;
    AppointmentRepository appointmentRepository;

    @Override
    public AppointmentDTO createAppointment(AppointmentRequest appointmentRequest, long senderId, long recipientId) {
        Appointment appointment = appointmentMapper.toAppointment(appointmentRequest);

        appointment.addSender(userRepository.findById(senderId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
        appointment.addRecipient(userRepository.findById(recipientId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
        appointment.setAppointmentNo();
        appointment.setStatus(AppointmentStatus.WAITING_FOR_APPROVAL);

        appointmentRepository.save(appointment);

        return appointmentMapper.toAppointmentDTO(appointment);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::toAppointmentDTO).toList();
    }

    @Override
    public AppointmentDTO updateAppointment(long id, UpdateAppointmentRequest updateAppointmentRequest) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));

        if (!appointment.getStatus().equals(AppointmentStatus.WAITING_FOR_APPROVAL)) {
            throw new AppException(ErrorCode.CANNOT_UPDATE);
        }

        appointmentMapper.updateAppointment(appointment, updateAppointmentRequest);

        return appointmentMapper.toAppointmentDTO(appointmentRepository.save(appointment));
    }

    @Override
    public boolean deleteAppointment(long id) {
        if (userRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        } else throw new AppException(ErrorCode.NO_DATA_FOUND);
    }

    @Override
    public AppointmentDTO getAppointmentById(long id) {
        return appointmentMapper.toAppointmentDTO(appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
    }

    @Override
    public AppointmentDTO getAppointmentByNo(String no) {
        return appointmentMapper.toAppointmentDTO(appointmentRepository.findByAppointmentNo(no)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
    }
}