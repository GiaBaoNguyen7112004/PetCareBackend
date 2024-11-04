package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.payload.request.BookAppointmentRequest;
import com.personalproject.universal_pet_care.payload.request.UpdateAppointmentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PetMapper.class})
public interface AppointmentMapper {
    Appointment toAppointment(BookAppointmentRequest bookAppointmentRequest);

    @Mapping(source = "sender", target = "sender") // Tự động mapping sender
    @Mapping(source = "recipient", target = "recipient") // Tự động mapping recipient
    @Mapping(source = "pets", target = "pets")
    AppointmentDTO toAppointmentDTO(Appointment appointment);

    void updateAppointment(@MappingTarget Appointment appointment, UpdateAppointmentRequest updateAppointmentRequest);
}
