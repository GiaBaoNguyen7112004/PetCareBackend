package com.personalproject.universal_pet_care.factory;

import com.personalproject.universal_pet_care.payload.request.registration.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;

public interface UserFactory {
    User createUser(RegistrationRequest registrationRequest);
}
