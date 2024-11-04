package com.personalproject.universal_pet_care.service.pet;

import com.personalproject.universal_pet_care.dto.PetDTO;
import com.personalproject.universal_pet_care.entity.Pet;
import com.personalproject.universal_pet_care.payload.request.UpdatePetRequest;

import java.util.List;

public interface IPetService {
    List<Pet> savePetForAppointment(List<Pet> pets);

    PetDTO updatePet(long id, UpdatePetRequest updatePetRequest);

    PetDTO getPetById(long id);

    void deletePet(long id);

    List<PetDTO> getAllPets();
}
