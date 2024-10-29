package com.personalproject.universal_pet_care.service.user;

import com.personalproject.universal_pet_care.dto.UserDTO;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.UserMapper;
import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;

import com.personalproject.universal_pet_care.factory.UserFactory;
import com.personalproject.universal_pet_care.payload.request.UpdateUserRequest;
import com.personalproject.universal_pet_care.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService{
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserFactory userFactory;
    UserMapper userMapper;
    UserRepository userRepository;

    @Override
    public UserDTO register(RegistrationRequest registrationRequest) {
        return userMapper.toUserDTO(userFactory.createUser(registrationRequest));
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        userMapper.updateUser(user, updateUserRequest);

        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(Long id)
    {
        return userMapper.toUserDTO(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
    }

    @Override
    public boolean deleteUser(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        userRepository.delete(user);

        return true;
    }

    @Override
    public List<UserDTO> getAllUsers()
    {
        return userRepository.findAll().stream().map(userMapper::toUserDTO).toList();
    }

}
