package com.personalproject.universal_pet_care.config;

import com.personalproject.universal_pet_care.entity.Role;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.enums.UserType;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
@Slf4j
public class AppInitConfig {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

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
                Role role = Role.builder()
                        .name("ROLE_" + UserType.ADMIN).build();

                User user = User.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .firstName(adminFirstName)
                        .roles(Collections.singletonList(role))
                        .build();

                log.warn(FeedbackMessage.INITIATE_ADMIN);
                userRepository.save(user);
            }
        };
    }
}