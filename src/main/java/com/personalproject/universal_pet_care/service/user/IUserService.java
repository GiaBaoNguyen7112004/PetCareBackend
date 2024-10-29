package com.personalproject.universal_pet_care.service.user;

import com.personalproject.universal_pet_care.dto.UserDTO;
import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.payload.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {
    UserDTO register(RegistrationRequest registrationRequest);
    UserDTO updateUser(Long id, UpdateUserRequest updateUserRequest);
    UserDTO getUserById(Long id);
    boolean deleteUser(Long id);

    List<UserDTO> getAllUsers();
}
