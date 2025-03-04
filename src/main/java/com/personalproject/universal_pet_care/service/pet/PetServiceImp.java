package com.personalproject.universal_pet_care.service.pet;


import com.personalproject.universal_pet_care.dto.PetDTO;
import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.entity.Pet;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.PetMapper;
import com.personalproject.universal_pet_care.payload.request.appointment.AppointmentBookingRequest;
import com.personalproject.universal_pet_care.payload.request.pet.PetUpdatingRequest;
import com.personalproject.universal_pet_care.repository.PetRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PetServiceImp implements PetService {
    PetRepository petRepository;
    PetMapper petMapper;

    @Override
    public List<Pet> savePetForAppointment(Appointment appointment,
                                           AppointmentBookingRequest appointmentBookingRequest) {
        List<Pet> pets = appointmentBookingRequest.getPetRegistrationRequests().stream().map(petMapper::toPet)
                .toList();
        pets.forEach(pet -> pet.setAppointment(appointment));
        return petRepository.saveAll(pets);
    }

    @Override
    public PetDTO updatePet(long id, PetUpdatingRequest petUpdatingRequest) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        petMapper.updatePet(pet, petUpdatingRequest);

        return petMapper.toPetDTO(petRepository.save(pet));
    }

    @Override
    public PetDTO getPetById(long id) {
        return petMapper.toPetDTO(petRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
    }

    @Override
    public void deletePet(long id) {
        petRepository.deleteById(id);
    }

    @Override
    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream().map(petMapper::toPetDTO).toList();
    }
}
