package com.personalproject.universal_pet_care.factory;


import com.personalproject.universal_pet_care.enums.UserType;
import com.personalproject.universal_pet_care.payload.request.registration.AdminRegistrationRequest;
import com.personalproject.universal_pet_care.entity.Admin;
import com.personalproject.universal_pet_care.mapper.UserMapper;
import com.personalproject.universal_pet_care.repository.user.AdminRepository;
import com.personalproject.universal_pet_care.service.role.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminFactory {
    AdminRepository adminRepository;
    UserMapper userMapper;
    RoleService roleService;

    public Admin createAdmin(AdminRegistrationRequest registrationRequest) {
        Admin admin = new Admin();
        userMapper.toUser(admin, registrationRequest);
        admin.setRoles(roleService.getRolesForUser(UserType.ADMIN.toString()));

        return adminRepository.save(admin);
    }
}
