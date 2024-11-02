package com.personalproject.universal_pet_care.factory;

import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleUserFactory implements UserFactory{
    AdminFactory adminFactory;
    VeterinarianFactory veterinarianFactory;
    PatientFactory patientFactory;
    UserRepository userRepository;

    @Override
    public User createUser(RegistrationRequest registrationRequest) {
        if(userRepository.existsByEmail(registrationRequest.getEmail())) throw new AppException(ErrorCode.USER_EXIST);

        switch (registrationRequest.getUserType())
        {
            case "ADMIN" -> {return adminFactory.createAdmin(registrationRequest);}
            case "VETERINARIAN" -> {return veterinarianFactory.createVeterinarian(registrationRequest);}
            case "PATIENT" -> {return patientFactory.createPatient(registrationRequest);}
            default -> {return null;}
        }
    }
}
