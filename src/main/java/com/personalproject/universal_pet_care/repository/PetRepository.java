package com.personalproject.universal_pet_care.repository;

import com.personalproject.universal_pet_care.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
