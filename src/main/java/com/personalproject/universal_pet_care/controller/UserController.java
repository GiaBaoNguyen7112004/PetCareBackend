package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.payload.request.UpdateUserRequest;
import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.user.IUserService;

import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.USERS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    IUserService iUserService;

    @PostMapping(UrlMapping.REGISTER_USER)
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iUserService.register(registrationRequest))
                .message(FeedbackMessage.CREATE_SUCCESS)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping(UrlMapping.UPDATE_USER)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iUserService.updateUser(id, updateUserRequest))
                .message(FeedbackMessage.UPDATE_SUCCESS)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(UrlMapping.GET_USER_BY_ID)
    public ResponseEntity<?> getUserById(@PathVariable Long id)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iUserService.getUserById(id))
                .message(FeedbackMessage.FOUND_SUCCESS)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping(UrlMapping.DELETE_USER_BY_ID)
    public ResponseEntity<?> deleteUserById(@PathVariable Long id)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iUserService.deleteUser(id))
                .message(FeedbackMessage.DELETE_SUCCESS)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(UrlMapping.GET_ALL_USERS)
    public ResponseEntity<?> getAllUsers()
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iUserService.getAllUsers())
                .message(FeedbackMessage.FOUND_SUCCESS)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
