package com.personalproject.universal_pet_care.config;

import com.personalproject.universal_pet_care.entity.Role;
import com.personalproject.universal_pet_care.enums.UserType;
import com.personalproject.universal_pet_care.factory.UserFactory;
import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.repository.RoleRepository;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
@Slf4j
public class AppInitConfig {
    final UserRepository userRepository;
    final UserFactory userFactory;
    final RoleRepository roleRepository;

    @Value("${admin.email}")
    String adminEmail;

    @Value("${admin.password}")
    String adminPassword;

    @Value("${admin.firstName}")
    String adminFirstName;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner() {
        log.info("Initializing application");
        return args -> {
            if (!userRepository.existsByEmail(adminEmail)) {
                List<Role> roles = new ArrayList<>();
                for (UserType userType : UserType.values()) {
                    Role role = Role.builder()
                            .name("ROLE_" + userType.name())
                            .build();
                    roles.add(role);
                }
                roleRepository.saveAll(roles);

                RegistrationRequest registrationRequest = RegistrationRequest.builder()
                        .email(adminEmail)
                        .firstName(adminFirstName)
                        .password(adminPassword)
                        .userType(UserType.ADMIN.toString())
                        .isEnabled(true)
                        .build();

                userFactory.createUser(registrationRequest);
                log.warn(FeedbackMessage.INITIATE_ADMIN);
            }
        };
    }
}