package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.payload.request.UserUpdatingRequest;
import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.user.UserService;

import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping(UrlMapping.USERS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping(UrlMapping.REGISTER_USER)
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.register(registrationRequest))
                .message(FeedbackMessage.CREATE_SUCCESS)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping(UrlMapping.UPDATE_USER)
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdatingRequest userUpdatingRequest)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.updateUser(id, userUpdatingRequest))
                .message(FeedbackMessage.UPDATE_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_USER_BY_ID)
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.getUserById(id))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping(UrlMapping.DELETE_USER_BY_ID)
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id)
    {
        userService.deleteUser(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.DELETE_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_ALL_USERS)
    public ResponseEntity<ApiResponse> getAllUsers()
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.getAllUsers())
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_USER_DETAILS)
    public ResponseEntity<ApiResponse> getUserDetails(@PathVariable Long id)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.getUserDetails(id))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }
}
