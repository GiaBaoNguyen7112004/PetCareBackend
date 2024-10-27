package com.personalproject.universal_pet_care.controller;

import com.personalproject.universal_pet_care.dto.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    UserService userService;

    @PostMapping
    public User createUser(@RequestBody RegistrationRequest registrationRequest) {
        log.info(String.valueOf(registrationRequest.isEnabled()));
        return userService.createUser(registrationRequest);
    }
}
