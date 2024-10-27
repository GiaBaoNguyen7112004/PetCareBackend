package com.personalproject.universal_pet_care.service;

import com.personalproject.universal_pet_care.dto.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.factory.UserFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserFactory userFactory;

    public User createUser(RegistrationRequest registrationRequest) {
        return userFactory.createUser(registrationRequest);
    }
}
