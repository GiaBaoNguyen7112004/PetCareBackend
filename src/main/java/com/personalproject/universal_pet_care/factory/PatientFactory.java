package com.personalproject.universal_pet_care.factory;


import com.personalproject.universal_pet_care.enums.UserType;
import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.Patient;
import com.personalproject.universal_pet_care.mapper.UserMapper;

import com.personalproject.universal_pet_care.repository.user.PatientRepository;
import com.personalproject.universal_pet_care.service.role.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientFactory {
    PatientRepository patientRepository;
    UserMapper userMapper;
    RoleService roleService;

    public Patient createPatient(RegistrationRequest registrationRequest)
    {
        Patient patient = new Patient();
        userMapper.toUser(patient, registrationRequest);
        patient.setRoles(roleService.getRolesForUser(UserType.PATIENT.toString()));

        return patientRepository.save(patient);
    }
}
