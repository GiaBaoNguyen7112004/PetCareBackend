package com.personalproject.universal_pet_care.service.user;

import com.personalproject.universal_pet_care.dto.UserDTO;
import com.personalproject.universal_pet_care.payload.request.registration.RegistrationRequest;
import com.personalproject.universal_pet_care.payload.request.user.UserUpdatingRequest;

import java.util.List;

public interface UserService {
    UserDTO register(RegistrationRequest registrationRequest);

    UserDTO updateUser(Long id, UserUpdatingRequest userUpdatingRequest);

    UserDTO getUserById(Long id);

    void deleteUser(Long id);

    List<UserDTO> getAllUsers();

    UserDTO getUserDetails(Long userId);
}
