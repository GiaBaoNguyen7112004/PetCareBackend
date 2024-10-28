package com.personalproject.universal_pet_care.factory;

import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;

public interface UserFactory {
    public User createUser(RegistrationRequest registrationRequest);
}
