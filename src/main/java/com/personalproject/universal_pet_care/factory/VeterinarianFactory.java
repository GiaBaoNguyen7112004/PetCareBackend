package com.personalproject.universal_pet_care.factory;

import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.Veterinarian;
import com.personalproject.universal_pet_care.mapper.UserMapper;
import com.personalproject.universal_pet_care.repository.user.VeterinarianRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VeterinarianFactory {
    VeterinarianRepository veterinarianRepository;
    UserMapper userMapper;

    public Veterinarian createVeterinarian(RegistrationRequest registrationRequest) {
        Veterinarian veterinarian = new Veterinarian();
        userMapper.toUser(veterinarian, registrationRequest);
        veterinarian.setSpecialization(registrationRequest.getSpecialization());

        return veterinarianRepository.save(veterinarian);
    }
}
