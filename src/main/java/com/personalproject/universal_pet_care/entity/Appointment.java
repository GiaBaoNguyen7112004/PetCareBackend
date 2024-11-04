package com.personalproject.universal_pet_care.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity(name = "appointments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String reason;

    @JsonFormat(pattern = "HH:mm")
    LocalTime appointmentTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate appointmentDate;

    String appointmentNo;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    AppointmentStatus status;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    User sender;

    @JoinColumn(name = "recipient_id")
    @ManyToOne
    User recipient;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    List<Pet> pets;

    public void addSender(User sender)
    {
        this.sender = sender;
        if(sender instanceof Patient patient)
        {
            if(Objects.isNull(patient.getAppointments()))
                patient.setAppointments(new ArrayList<>());

            patient.getAppointments().add(this);
        }
    }

    public void addRecipient(User recipient)
    {
        this.recipient = recipient;
        if(recipient instanceof Veterinarian veterinarian)
        {
            if(Objects.isNull(veterinarian.getAppointments()))
                veterinarian.setAppointments(new ArrayList<>());

            veterinarian.getAppointments().add(this);
        }
    }

    public void setAppointmentNo()
    {
        this.appointmentNo = String.valueOf(UUID.randomUUID()).substring(1,11);
    }

}
