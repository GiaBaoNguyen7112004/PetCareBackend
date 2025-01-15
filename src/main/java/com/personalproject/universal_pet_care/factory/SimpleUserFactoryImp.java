package com.personalproject.universal_pet_care.factory;

import com.personalproject.universal_pet_care.enums.UserType;
import com.personalproject.universal_pet_care.event.UserRegistrationEvent;
import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleUserFactoryImp implements UserFactory{
    AdminFactory adminFactory;
    VeterinarianFactory veterinarianFactory;
    PatientFactory patientFactory;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User createUser(RegistrationRequest registrationRequest) {
        if(userRepository.existsByEmail(registrationRequest.getEmail())) throw new AppException(ErrorCode.USER_EXIST);
        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        try {
            UserType userType = UserType.valueOf(registrationRequest.getUserType());

            User user = switch (userType) {
                case ADMIN -> adminFactory.createAdmin(registrationRequest);
                case VETERINARIAN -> veterinarianFactory.createVeterinarian(registrationRequest);
                case PATIENT -> patientFactory.createPatient(registrationRequest);
            };

            applicationEventPublisher.publishEvent(new UserRegistrationEvent(user));
            return user;
        }
        catch (DataIntegrityViolationException e) {throw new AppException(ErrorCode.USER_EXIST);}
        catch(IllegalArgumentException e){throw new AppException(ErrorCode.INVALID_DATA);}
    }
}
