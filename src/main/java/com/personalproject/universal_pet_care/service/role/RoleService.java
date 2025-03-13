package com.personalproject.universal_pet_care.service.role;

import com.personalproject.universal_pet_care.dto.RoleDTO;
import com.personalproject.universal_pet_care.entity.Role;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.RoleMapper;
import com.personalproject.universal_pet_care.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleDTO).toList();
    }

    public RoleDTO getRoleById(Long id) {
        return roleRepository.findById(id).map(roleMapper::toRoleDTO)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

    public RoleDTO getRoleByName(String name) {
        return roleRepository.findByName(name).map(roleMapper::toRoleDTO)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }


    public Set<Role> getRolesForUser(String userType) {
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName("ROLE_" + userType.toUpperCase()).ifPresentOrElse(roles::add,
                () -> {
                    throw new AppException(ErrorCode.NO_DATA_FOUND);
                });
        return roles;
    }
}

