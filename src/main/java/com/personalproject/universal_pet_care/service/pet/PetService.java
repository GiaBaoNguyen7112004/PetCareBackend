package com.personalproject.universal_pet_care.service.pet;

import com.personalproject.universal_pet_care.dto.PetDTO;
import com.personalproject.universal_pet_care.entity.Pet;
import com.personalproject.universal_pet_care.payload.request.PetUpdatingRequest;

import java.util.List;

public interface PetService {
    void savePetForAppointment(List<Pet> pets);

    PetDTO updatePet(long id, PetUpdatingRequest petUpdatingRequest);

    PetDTO getPetById(long id);

    void deletePet(long id);

    List<PetDTO> getAllPets();
}
