package com.personalproject.universal_pet_care.event.listener;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.event.UserRegistrationEvent;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
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
            default: throw new AppException(ErrorCode.INVALID_DATA);
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
        String subject = "Email Verification - Universal Pet Care";
        String senderName = "Universal Pet Care Team";
        String mailContent = "<p>Dear " + user.getFirstName() + ",</p>" +
                "<p>Thank you for registering with <strong>Universal Pet Care</strong>. " +
                "To complete your registration, please verify your email address by clicking the link below:</p>" +
                "<p><a href=\"" + url + "\" style=\"color: #007bff; text-decoration: none;\">Verify Your Email</a></p>" +
                "<p>If you did not sign up for Universal Pet Care, please disregard this message.</p>" +
                "<p>Thank you,<br>The Universal Pet Care Team</p>";
        emailService.sendEmail(user.getEmail(), subject, senderName, mailContent);
    }
//    end user registration verification email
}
