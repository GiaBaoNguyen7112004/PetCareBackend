package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.AppointmentDTO;
import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.payload.request.AppointmentRequest;
import com.personalproject.universal_pet_care.payload.request.UpdateAppointmentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AppointmentMapper {
    Appointment toAppointment(AppointmentRequest appointmentRequest);

    @Mapping(source = "sender", target = "sender") // Tự động mapping sender
    @Mapping(source = "recipient", target = "recipient") // Tự động mapping recipient
    AppointmentDTO toAppointmentDTO(Appointment appointment);

    void updateAppointment(@MappingTarget Appointment appointment, UpdateAppointmentRequest updateAppointmentRequest);
}
