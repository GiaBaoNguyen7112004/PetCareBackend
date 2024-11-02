package com.personalproject.universal_pet_care.repository.user;

import com.personalproject.universal_pet_care.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
