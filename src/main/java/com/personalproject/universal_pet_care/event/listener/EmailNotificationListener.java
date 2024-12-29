package com.personalproject.universal_pet_care.event.listener;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.event.UserRegistrationEvent;
import com.personalproject.universal_pet_care.payload.request.VerificationTokenCreationRequest;
import com.personalproject.universal_pet_care.service.email.EmailService;
import com.personalproject.universal_pet_care.service.token.VerificationTokenService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailNotificationListener{
    final EmailService emailService;
    final VerificationTokenService verificationTokenService;

    @Value("${frontend.base.url}")
    String frontendBaseUrl;

    @EventListener
    public void handleEvent(ApplicationEvent event) throws MessagingException, UnsupportedEncodingException {
        switch (event.getClass().getSimpleName()){
            case "UserRegistrationEvent": handleUserRegistrationEvent((UserRegistrationEvent) event); break;
        }
    }

//  begin  user registration verification email
    private void handleUserRegistrationEvent(UserRegistrationEvent event)
            throws MessagingException, UnsupportedEncodingException {
        User user = event.getUser();
        String verificationToken = UUID.randomUUID().toString();

        verificationTokenService.saveVerificationTokenForUser(VerificationTokenCreationRequest.builder()
                        .userId(user.getId())
                        .token(verificationToken)
                .build());

        String verificationUrl = frontendBaseUrl + "/email-verification?token=" + verificationToken;
        sendUserRegistrationVerificationEmail(user, verificationUrl);
    }

    private void sendUserRegistrationVerificationEmail(User user, String url)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Verify Your Email";
        String senderName = "Universal Pet Care";
        String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>" +
                "<p>Thank you for registering with us," +
                "Please, follow the link below to complete your registration.</p>" +
                "<a href=\"" + url + "\">Verify your email</a>" +
                "<p> Thank you <br> Universal Pet Care Email Verification Service";

        emailService.sendEmail(user.getEmail(), subject, senderName, mailContent);
    }
//    end user registration verification email
}
