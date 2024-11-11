package com.personalproject.universal_pet_care.service.appointment;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.entity.Pet;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.AppointmentMapper;
import com.personalproject.universal_pet_care.mapper.PetMapper;
import com.personalproject.universal_pet_care.payload.request.AppointmentBookingRequest;
import com.personalproject.universal_pet_care.payload.request.AppointmentUpdatingRequest;
import com.personalproject.universal_pet_care.repository.AppointmentRepository;

import com.personalproject.universal_pet_care.repository.user.UserRepository;
import com.personalproject.universal_pet_care.service.pet.IPetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    AppointmentMapper appointmentMapper;
    UserRepository userRepository;
    AppointmentRepository appointmentRepository;
    PetMapper petMapper;
    IPetService iPetService;

    @Transactional
    @Override
    public AppointmentDTO createAppointment(AppointmentBookingRequest appointmentBookingRequest, long senderId, long recipientId) {
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
        iPetService.savePetForAppointment(pets);
        appointment.setPets(pets);

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
            throw new AppException(ErrorCode.CANNOT_UPDATE);
        }

        appointmentMapper.updateAppointment(appointment, appointmentUpdatingRequest);

        return appointmentMapper.toAppointmentDTO(appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointment(long id) {
        appointmentRepository.findById(id).ifPresentOrElse(appointmentRepository::delete,
                () -> {throw new AppException(ErrorCode.NO_DATA_FOUND);});
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