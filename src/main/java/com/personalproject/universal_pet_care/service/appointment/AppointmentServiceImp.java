package com.personalproject.universal_pet_care.service.appointment;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.entity.Appointment;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import com.personalproject.universal_pet_care.event.AppointmentApprovedEvent;
import com.personalproject.universal_pet_care.event.AppointmentBookedEvent;
import com.personalproject.universal_pet_care.event.AppointmentDeclinedEvent;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.AppointmentMapper;

import com.personalproject.universal_pet_care.payload.request.AppointmentBookingRequest;
import com.personalproject.universal_pet_care.payload.request.AppointmentUpdatingRequest;
import com.personalproject.universal_pet_care.repository.AppointmentRepository;

import com.personalproject.universal_pet_care.repository.user.UserRepository;
import com.personalproject.universal_pet_care.scheduler.AppointmentStatusUpdater;
import com.personalproject.universal_pet_care.service.pet.PetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;


import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AppointmentServiceImp implements AppointmentService {
    AppointmentMapper appointmentMapper;
    UserRepository userRepository;
    AppointmentRepository appointmentRepository;
    PetService petService;
    ApplicationEventPublisher applicationEventPublisher;
    AppointmentStatusUpdater appointmentStatusUpdater;

    @Transactional
    @Override
    public AppointmentDTO bookAppointment(AppointmentBookingRequest appointmentBookingRequest, long senderId, long recipientId) {
        if (LocalDateTime.of(appointmentBookingRequest.getAppointmentDate(),
                appointmentBookingRequest.getAppointmentTime()).isBefore(LocalDateTime.now()))
            throw new AppException(ErrorCode.INVALID_TIME);

        Appointment appointment = appointmentMapper.toAppointment(appointmentBookingRequest);
        appointment.addSender(getUserById(senderId));
        appointment.addRecipient(getUserById(recipientId));
        appointment.setAppointmentNo();
        appointment.setStatus(AppointmentStatus.WAITING_FOR_APPROVAL);
        appointment.setPets(petService.savePetForAppointment(appointment, appointmentBookingRequest));

        applicationEventPublisher.publishEvent(new AppointmentBookedEvent(appointment));
        return appointmentMapper.toAppointmentDTO(appointmentRepository.save(appointment));
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::toAppointmentDTO).toList();
    }

    @Override
    public AppointmentDTO updateAppointment(long id, AppointmentUpdatingRequest appointmentUpdatingRequest) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        if (!appointment.getStatus().equals(AppointmentStatus.WAITING_FOR_APPROVAL))
            throw new AppException(ErrorCode.APPOINTMENT_UPDATE_FAILED);

        appointmentMapper.updateAppointment(appointment, appointmentUpdatingRequest);
        appointmentStatusUpdater.scheduleStatusUpdate(appointment.getId());

        return appointmentMapper.toAppointmentDTO(appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointment(long id) {
        appointmentStatusUpdater.cancelScheduledTasks(id);
        appointmentRepository.deleteById(id);
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

    @Override
    public List<AppointmentDTO> getAppointmentByUserId(Long id) {
        return appointmentRepository.findByUserId(id).stream().map(appointmentMapper::toAppointmentDTO).toList();
    }

    @Override
    public AppointmentDTO cancelAppointment(Long id) {
        return appointmentRepository.findById(id).filter(
                        appointment -> appointment.getStatus().equals(AppointmentStatus.WAITING_FOR_APPROVAL))
                .map(appointment -> {
                    appointment.setStatus(AppointmentStatus.CANCELLED);
                    return appointmentMapper.toAppointmentDTO(appointmentRepository.save(appointment));
                })
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
    }

    @Override
    public AppointmentDTO approveAppointment(Long id) {
        return appointmentRepository.findById(id).filter(
                        appointment -> appointment.getStatus().equals(AppointmentStatus.WAITING_FOR_APPROVAL))
                .map(appointment -> {
                    appointment.setStatus(AppointmentStatus.APPROVED);
                    appointmentStatusUpdater.scheduleStatusUpdate(appointment.getId());
                    applicationEventPublisher.publishEvent(new AppointmentApprovedEvent(appointment));
                    return appointmentMapper.toAppointmentDTO(appointmentRepository.save(appointment));
                })
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
    }

    @Override
    public AppointmentDTO declineAppointment(Long id) {
        return appointmentRepository.findById(id).filter(
                        appointment -> appointment.getStatus().equals(AppointmentStatus.WAITING_FOR_APPROVAL))
                .map(appointment -> {
                    appointment.setStatus(AppointmentStatus.NOT_APPROVED);
                    applicationEventPublisher.publishEvent(new AppointmentDeclinedEvent(appointment));
                    return appointmentMapper.toAppointmentDTO(appointmentRepository.save(appointment));
                })
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}