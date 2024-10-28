package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.UserDTO;
import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    void toUser(@MappingTarget  User user, RegistrationRequest registrationRequest);
    @Mapping(target = "isEnabled", source = "enabled")
    UserDTO toUserDTO(User user);
}
