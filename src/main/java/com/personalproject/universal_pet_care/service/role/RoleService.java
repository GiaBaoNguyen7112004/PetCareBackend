package com.personalproject.universal_pet_care.service.role;

import com.personalproject.universal_pet_care.entity.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRolesForUser(String userType);
}

