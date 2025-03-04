package com.personalproject.universal_pet_care.factory;

import com.personalproject.universal_pet_care.enums.UserType;
import com.personalproject.universal_pet_care.entity.Veterinarian;
import com.personalproject.universal_pet_care.mapper.UserMapper;
import com.personalproject.universal_pet_care.payload.request.registration.VeterinarianRegistrationRequest;
import com.personalproject.universal_pet_care.repository.user.VeterinarianRepository;
import com.personalproject.universal_pet_care.service.role.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VeterinarianFactory {
    VeterinarianRepository veterinarianRepository;
    UserMapper userMapper;
    RoleService roleService;

    public Veterinarian createVeterinarian(VeterinarianRegistrationRequest registrationRequest) {
        Veterinarian veterinarian = new Veterinarian();
        log.info("tao da o day");
        userMapper.toUser(veterinarian, registrationRequest);
        veterinarian.setSpecialization(registrationRequest.getSpecialization());
        veterinarian.setRoles(roleService.getRolesForUser(UserType.VETERINARIAN.toString()));

        return veterinarianRepository.save(veterinarian);
    }
}
