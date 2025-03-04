package com.personalproject.universal_pet_care.payload.request.registration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VeterinarianRegistrationRequest.class, name = "VETERINARIAN"),
        @JsonSubTypes.Type(value = AdminRegistrationRequest.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = PatientRegistrationRequest.class, name = "PATIENT")
})
public class RegistrationRequest {
    @NotBlank(message = "MISSING_FIELD")
    String firstName;

    @NotBlank(message = "MISSING_FIELD")
    String lastName;

    String gender;

    @NotBlank(message = "MISSING_FIELD")
    String phoneNumber;

    @NotBlank(message = "MISSING_FIELD")
    @Email(message = "INVALID_EMAIL")
    String email;

    @NotBlank(message = "MISSING_FIELD")
    @Size(min = 6, max = 20, message = "INVALID_PASSWORD_LENGTH")
    @Pattern(regexp = "^[a-zA-Z0-9@#$%^&*]+$", message = "INVALID_PASSWORD_CONTENT")
    String password;

    String userType;
    boolean isEnabled;
}
