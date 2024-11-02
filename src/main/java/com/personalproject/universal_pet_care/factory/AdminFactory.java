package com.personalproject.universal_pet_care.factory;


import com.personalproject.universal_pet_care.payload.request.RegistrationRequest;
import com.personalproject.universal_pet_care.entity.Admin;
import com.personalproject.universal_pet_care.mapper.UserMapper;
import com.personalproject.universal_pet_care.repository.user.AdminRepository;
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

    public Admin createAdmin(RegistrationRequest registrationRequest) {
        Admin admin = new Admin();
        userMapper.toUser(admin, registrationRequest);

        return adminRepository.save(admin);
    }
}
