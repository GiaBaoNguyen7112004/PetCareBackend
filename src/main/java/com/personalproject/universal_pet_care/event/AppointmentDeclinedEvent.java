package com.personalproject.universal_pet_care.event;

import com.personalproject.universal_pet_care.entity.Appointment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentDeclinedEvent extends ApplicationEvent {
    Appointment appointment;

    public AppointmentDeclinedEvent(Appointment appointment) {
        super(appointment);
        this.appointment = appointment;
    }
}
