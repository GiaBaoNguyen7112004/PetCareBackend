package com.personalproject.universal_pet_care.factory;

import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.Patient;
import com.personalproject.universal_pet_care.mapper.UserMapper;
import com.personalproject.universal_pet_care.repository.user.PatientRepository;
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

    public Patient createPatient(RegistrationRequest registrationRequest)
    {
        Patient patient = new Patient();
        userMapper.toUser(patient, registrationRequest);

        return patientRepository.save(patient);
    }
}
