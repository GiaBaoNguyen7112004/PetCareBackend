package com.personalproject.universal_pet_care.service;

import com.personalproject.universal_pet_care.dto.UserDTO;
import com.personalproject.universal_pet_care.mapper.UserMapper;
import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;

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
    UserMapper userMapper;

    public UserDTO createUser(RegistrationRequest registrationRequest) {
        return userMapper.toUserDTO(userFactory.createUser(registrationRequest));
    }
}
