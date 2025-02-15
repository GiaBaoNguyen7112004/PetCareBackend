package com.personalproject.universal_pet_care.service.appointment;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.entity.Pet;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import com.personalproject.universal_pet_care.event.AppointmentApprovedEvent;
import com.personalproject.universal_pet_care.event.AppointmentBookedEvent;
import com.personalproject.universal_pet_care.event.AppointmentDeclinedEvent;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.AppointmentMapper;
import com.personalproject.universal_pet_care.mapper.PetMapper;
import com.personalproject.universal_pet_care.payload.request.AppointmentBookingRequest;
import com.personalproject.universal_pet_care.payload.request.AppointmentUpdatingRequest;
import com.personalproject.universal_pet_care.repository.AppointmentRepository;

import com.personalproject.universal_pet_care.repository.user.UserRepository;
import com.personalproject.universal_pet_care.service.pet.PetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AppointmentServiceImp implements AppointmentService {
    AppointmentMapper appointmentMapper;
    UserRepository userRepository;
    AppointmentRepository appointmentRepository;
    PetMapper petMapper;
    PetService petService;
    ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Override
    public AppointmentDTO bookAppointment(AppointmentBookingRequest appointmentBookingRequest, long senderId, long recipientId) {
        Appointment appointment = appointmentMapper.toAppointment(appointmentBookingRequest);

        appointment.addSender(userRepository.findById(senderId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
        appointment.addRecipient(userRepository.findById(recipientId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
        appointment.setAppointmentNo();
        appointment.setStatus(AppointmentStatus.WAITING_FOR_APPROVAL);

        List<Pet> pets = appointmentBookingRequest.getPetRegistrationRequests().stream().map(petMapper::toPet)
                .toList();
        pets.forEach(pet -> pet.setAppointment(appointment));
        petService.savePetForAppointment(pets);
        appointment.setPets(pets);

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
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));

        if (!appointment.getStatus().equals(AppointmentStatus.WAITING_FOR_APPROVAL)) {
            throw new AppException(ErrorCode.UPDATE_FAILED);
        }

        appointmentMapper.updateAppointment(appointment, appointmentUpdatingRequest);

        return appointmentMapper.toAppointmentDTO(appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointment(long id) {
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

    @Override
    public void setAppointmentStatus(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        LocalDate appointmentDate = appointment.getAppointmentDate();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalTime appointmentEndTime = appointment.getAppointmentTime()
                .plusMinutes(2).truncatedTo(ChronoUnit.MINUTES);

        switch (appointment.getStatus()) {
            case APPROVED -> {
                if ((currentDate.isBefore(appointmentDate) || currentDate.equals(appointmentDate))
                        && (currentTime.isBefore(appointmentEndTime) || currentTime.equals(appointmentEndTime))) {
                    appointment.setStatus(AppointmentStatus.UP_COMING);
                }
            }
            case UP_COMING -> {
                if (currentDate.equals(appointmentDate) && currentTime.isBefore(appointmentEndTime)) {
                    appointment.setStatus(AppointmentStatus.ON_GOING);
                }
            }

            case ON_GOING -> {
                if ((currentDate.isAfter(appointmentDate) || currentDate.equals(appointmentDate))
                        && currentTime.isAfter(appointmentEndTime)) {
                    appointment.setStatus(AppointmentStatus.COMPLETED);
                }
            }

            case WAITING_FOR_APPROVAL -> {
                if ((currentDate.equals(appointmentDate) || currentDate.isAfter(appointmentDate))
                        && currentTime.isAfter(appointmentEndTime)) {
                    appointment.setStatus(AppointmentStatus.NOT_APPROVED);
                }
            }
            default -> throw new AppException(ErrorCode.APPOINTMENT_NOT_FOUND);
        }
        appointmentRepository.save(appointment);
    }
}