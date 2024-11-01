package com.personalproject.universal_pet_care.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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

    public void addSender(User sender)
    {
        this.sender = (Patient) sender;
        if(sender instanceof Patient)
        {
            Patient patient = (Patient) sender;
            if(Objects.isNull(patient.getAppointments()))
                patient.setAppointments(new ArrayList<Appointment>());

            patient.getAppointments().add(this);
        }
    }

    public void addRecipient(User recipient)
    {
        this.recipient = (Veterinarian) recipient;
        if(recipient instanceof Veterinarian)
        {
            Veterinarian veterinarian = (Veterinarian) recipient;
            if(Objects.isNull(veterinarian.getAppointments()))
                veterinarian.setAppointments(new ArrayList<Appointment>());

            veterinarian.getAppointments().add(this);
        }
    }

    public void setAppointmentNo()
    {
        this.appointmentNo = String.valueOf(new Random().nextLong()).substring(1,11);
    }

}
