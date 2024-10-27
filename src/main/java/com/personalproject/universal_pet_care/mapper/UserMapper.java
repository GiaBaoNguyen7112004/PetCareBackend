package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    void toUser(@MappingTarget  User user, RegistrationRequest registrationRequest);
}
