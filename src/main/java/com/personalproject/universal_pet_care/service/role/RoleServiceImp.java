package com.personalproject.universal_pet_care.service.role;

import com.personalproject.universal_pet_care.entity.Role;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {
    RoleRepository roleRepository;

    @Override
    public Set<Role> getRolesForUser(String userType) {
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName("ROLE_" + userType.toUpperCase()).ifPresentOrElse(roles::add,
                () -> {throw new AppException(ErrorCode.NO_DATA_FOUND);});
        return roles;
    }
}
