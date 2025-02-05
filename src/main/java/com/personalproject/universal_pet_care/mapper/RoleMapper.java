package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.RoleDTO;
import com.personalproject.universal_pet_care.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface RoleMapper {
    RoleDTO toRoleDTO(Role role);
}
