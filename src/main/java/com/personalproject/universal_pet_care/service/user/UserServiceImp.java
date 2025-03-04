package com.personalproject.universal_pet_care.service.user;

import com.personalproject.universal_pet_care.dto.UserDTO;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.entity.Veterinarian;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.UserMapper;
import com.personalproject.universal_pet_care.payload.request.registration.PatientRegistrationRequest;
import com.personalproject.universal_pet_care.payload.request.registration.RegistrationRequest;

import com.personalproject.universal_pet_care.factory.UserFactory;
import com.personalproject.universal_pet_care.payload.request.user.UserUpdatingRequest;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import com.personalproject.universal_pet_care.service.appointment.AppointmentService;
import com.personalproject.universal_pet_care.service.review.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    UserFactory userFactory;
    UserMapper userMapper;
    UserRepository userRepository;
    ReviewService reviewService;
    AppointmentService appointmentService;

    @Override
    public UserDTO register(RegistrationRequest registrationRequest) {
        if (registrationRequest instanceof PatientRegistrationRequest) {
            log.info("kIEM TRA KIEU CUA REGISTRATION REQUEST TRONG REGISTER o userservice");
        }
        return userMapper.toUserDTO(userFactory.createUser(registrationRequest));
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdatingRequest userUpdatingRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        userMapper.updateUser(user, userUpdatingRequest);

        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userMapper.toUserDTO(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDTO).toList();
    }

    @Override
    public UserDTO getUserDetails(Long userId) {
        return userRepository.findById(userId).map(user -> {
            UserDTO userDTO = userMapper.toUserDTO(user);
            if (user instanceof Veterinarian) {
                userDTO.setAverageStars(reviewService.getAverageStarForVet(userId));
            }

            userDTO.setAppointmentDTOs(appointmentService.getAppointmentByUserId(userId));
            userDTO.setReviewDTOs(reviewService.getAllReviewsOfUser(userId));

            return userDTO;
        }).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
    }
}
