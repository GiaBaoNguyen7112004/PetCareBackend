package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.payload.request.appointment.AppointmentBookingRequest;
import com.personalproject.universal_pet_care.payload.request.appointment.AppointmentUpdatingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PetMapper.class})
public interface AppointmentMapper {
    Appointment toAppointment(AppointmentBookingRequest appointmentBookingRequest);

    @Mapping(source = "sender", target = "sender") // Tự động mapping sender
    @Mapping(source = "recipient", target = "recipient") // Tự động mapping recipient
    @Mapping(source = "pets", target = "pets")
    AppointmentDTO toAppointmentDTO(Appointment appointment);

    void updateAppointment(@MappingTarget Appointment appointment, AppointmentUpdatingRequest appointmentUpdatingRequest);
}
