package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.UserDTO;
import com.personalproject.universal_pet_care.entity.Veterinarian;
import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.payload.request.UserUpdatingRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {


    void toUser(@MappingTarget  User user, RegistrationRequest registrationRequest);

    @Mapping(target = "isEnabled", source = "enabled")
    UserDTO toUserDTO(User user);
    @AfterMapping
    default void setAdditionalFields(User user, @MappingTarget UserDTO.UserDTOBuilder userDTO) {
        if (user instanceof Veterinarian veterinarian) {
            userDTO.specialization(veterinarian.getSpecialization());
        }
    }

    void updateUser(@MappingTarget  User user, UserUpdatingRequest userUpdatingRequest);
    @AfterMapping
    default void setUpdateAdditionalFields(@MappingTarget User user, UserUpdatingRequest userUpdatingRequest) {
        if(user instanceof Veterinarian veterinarian) {
            veterinarian.setSpecialization(userUpdatingRequest.getSpecialization());
        }
    }
}
