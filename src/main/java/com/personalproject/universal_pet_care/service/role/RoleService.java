package com.personalproject.universal_pet_care.service.role;

import com.personalproject.universal_pet_care.dto.RoleDTO;
import com.personalproject.universal_pet_care.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<RoleDTO> getAllRoles();

    RoleDTO getRoleById(Long id);

    RoleDTO getRoleByName(String name);

    Set<Role> getRolesForUser(String userType);
}

