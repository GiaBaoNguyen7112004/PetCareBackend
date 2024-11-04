package com.personalproject.universal_pet_care.service.pet;


import com.personalproject.universal_pet_care.dto.PetDTO;
import com.personalproject.universal_pet_care.entity.Pet;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.PetMapper;
import com.personalproject.universal_pet_care.payload.request.UpdatePetRequest;
import com.personalproject.universal_pet_care.repository.PetRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PetService implements IPetService {
    PetRepository petRepository;
    PetMapper petMapper;

    @Override
    public List<Pet> savePetForAppointment(List<Pet> pets)
    {
        return petRepository.saveAll(pets);
    }

    @Override
    public PetDTO updatePet(long id, UpdatePetRequest updatePetRequest)
    {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        petMapper.updatePet(pet, updatePetRequest);

        return petMapper.toPetDTO(petRepository.save(pet));
    }

    @Override
    public PetDTO getPetById(long id)
    {
        return petMapper.toPetDTO(petRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
    }

    @Override
    public void deletePet(long id)
    {
       petRepository.findById(id).ifPresentOrElse(petRepository::delete,
               () -> {throw new AppException(ErrorCode.NO_DATA_FOUND);});
    }

    @Override
    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream().map(petMapper::toPetDTO).toList();
    }
}
