package com.personalproject.universal_pet_care.event.listener;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.event.AppointmentApprovedEvent;
import com.personalproject.universal_pet_care.event.AppointmentBookedEvent;
import com.personalproject.universal_pet_care.event.AppointmentDeclinedEvent;
import com.personalproject.universal_pet_care.event.UserRegistrationEvent;

import com.personalproject.universal_pet_care.payload.request.VerificationTokenCreationRequest;
import com.personalproject.universal_pet_care.service.email.EmailService;
import com.personalproject.universal_pet_care.service.token.VerificationTokenService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailNotificationListener {
    final EmailService emailService;
    final VerificationTokenService verificationTokenService;

    @Value("${frontend.base.url}")
    String frontendBaseUrl;

    @EventListener
    public void handleEvent(ApplicationEvent event) throws MessagingException, UnsupportedEncodingException {
        log.info(event.getClass().getSimpleName());
        switch (event.getClass().getSimpleName()) {
            case "ServletWebServerInitializedEvent":
                break;
            case "UserRegistrationEvent":
                handleUserRegistrationEvent((UserRegistrationEvent) event);
                break;
            case "AppointmentBookedEvent":
                handleAppointmentBookingEvent((AppointmentBookedEvent) event);
                break;
            case "AppointmentApprovedEvent":
                handleAppointmentApprovedEvent((AppointmentApprovedEvent) event);
                break;
            case "AppointmentDeclinedEvent":
                handleAppointmentDeclinedEvent((AppointmentDeclinedEvent) event);
                break;
            default:
        }
    }

    //  begin  handler user registration verification email
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
//    end handle user registration verification email

    //    begin handle appointment booked mail
    private void handleAppointmentBookingEvent(AppointmentBookedEvent event)
            throws MessagingException, UnsupportedEncodingException {
        User veterinarian = event.getAppointment().getRecipient();
        String url = frontendBaseUrl;
        sendAppointmentBookingEmail(veterinarian, url);
    }

    private void sendAppointmentBookingEmail(User veterinarian, String url)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Notification of New Appointment";
        String senderName = "Universal Pet Care Team";
        String mailContent = "<p>Dear " + veterinarian.getFirstName() + ",</p>" +
                "<p>We are pleased to inform you that a new appointment has been scheduled for you.</p>" +
                "<p>Kindly click the link below to access the clinic portal and view the details of your appointment:</p>" +
                "<a href=\"" + url + "\">View Appointment Details</a><br/><br/>" +
                "<p>Should you have any questions or require further assistance, " +
                "please do not hesitate to contact us.</p>" +
                "<p>Warm regards,</p>" +
                "<p><strong>Universal Pet Care Services</strong></p>";
        emailService.sendEmail(veterinarian.getEmail(), subject, senderName, mailContent);
    }
//    end handle appointment booked mail

    //    begin handle appointment approved event
    private void handleAppointmentApprovedEvent(AppointmentApprovedEvent event)
            throws MessagingException, UnsupportedEncodingException {
        User patient = event.getAppointment().getSender();
        String url = frontendBaseUrl;
        sendAppointmentApprovedEmail(patient, url);
    }

    private void sendAppointmentApprovedEmail(User patient, String url)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Appointment Approval Notification";
        String senderName = "Universal Pet Care Notification Service";
        String mailContent = "<p>Dear " + patient.getFirstName() + ",</p>" +
                "<p>We are pleased to inform you that your appointment has been successfully approved.</p>" +
                "<p>To view the appointment details and veterinarian information, please click the link below:</p>" +
                "<a href=\"" + url + "\">Access Appointment Details</a><br/><br/>" +
                "<p>If you have any questions or require further assistance, do not hesitate to contact us.</p>" +
                "<p>Best regards,</p>" +
                "<p><strong>Universal Pet Care Notification Service</strong></p>";
        emailService.sendEmail(patient.getEmail(), subject, senderName, mailContent);

    }
//    end handle appointment approved event

    //    begin handle appointment declined event
    private void handleAppointmentDeclinedEvent(AppointmentDeclinedEvent event)
            throws MessagingException, UnsupportedEncodingException {
        User patient = event.getAppointment().getSender();
        String url = frontendBaseUrl;
        sendAppointmentDeclinedEmail(patient, url);
    }

    private void sendAppointmentDeclinedEmail(User patient, String url)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Appointment Not Approved Notification";
        String senderName = "Universal Pet Care Notification Service";
        String mailContent = "<p>Dear " + patient.getFirstName() + ",</p>" +
                "<p>We regret to inform you that your appointment could not be approved at this time" +
                ". We sincerely apologize for any inconvenience this may cause." +
                "</p>" +
                "<p>We kindly encourage you to reschedule your appointment for an alternative date " +
                "at your earliest convenience." +
                "</p>" +
                "<p>You can view more details and reschedule your appointment by clicking the link below:" +
                "</p>" +
                "<a href=\"" + url + "\">Access Appointment Details</a><br/><br/>" +
                "<p>If you require further assistance or have any questions, please do not hesitate to contact us." +
                "</p>" +
                "<p>Best regards,</p>" +
                "<p><strong>Universal Pet Care Notification Service</strong></p>";
        emailService.sendEmail(patient.getEmail(), subject, senderName, mailContent);
    }
//    end handle appointment declined event
}
