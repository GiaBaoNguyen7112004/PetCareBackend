package com.personalproject.universal_pet_care.repository.user;

import com.personalproject.universal_pet_care.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
