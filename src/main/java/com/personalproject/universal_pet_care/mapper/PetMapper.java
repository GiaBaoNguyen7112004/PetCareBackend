package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.PetDTO;

import com.personalproject.universal_pet_care.entity.Pet;

import com.personalproject.universal_pet_care.payload.request.RegisterPetRequest;
import com.personalproject.universal_pet_care.payload.request.UpdatePetRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PetMapper {
    Pet toPet(RegisterPetRequest registerPetRequest);
    void updatePet(@MappingTarget Pet pet, UpdatePetRequest updatePetRequest);
    PetDTO toPetDTO(Pet pet);
    @AfterMapping
    default void setAdditionalFields(Pet pet, @MappingTarget PetDTO.PetDTOBuilder petDTO){
        petDTO.appointmentId(pet.getAppointment().getId());
    }

    List<PetDTO> toPetDTOs(List<Pet> pets);
}
