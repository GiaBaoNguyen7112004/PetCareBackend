package com.personalproject.universal_pet_care.repository;

import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAppointmentNo(String appointmentNo);
    boolean existsBySenderIdAndRecipientIdAndStatus(Long senderId, Long receiverId, AppointmentStatus status);
    @Query("SELECT a FROM appointments a WHERE a.sender.id = :userId OR a.recipient.id = :userId")
    List<Appointment> findByUserId(@Param("userId") Long userId);
}
