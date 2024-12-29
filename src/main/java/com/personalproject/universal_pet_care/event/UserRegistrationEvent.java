package com.personalproject.universal_pet_care.event;

import com.personalproject.universal_pet_care.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistrationEvent extends ApplicationEvent {
    User user;

    public UserRegistrationEvent(User user) {
        super(user);
        this.user = user;
    }
}
