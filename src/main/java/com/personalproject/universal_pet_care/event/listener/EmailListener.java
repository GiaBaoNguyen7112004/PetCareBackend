package com.personalproject.universal_pet_care.event.listener;

import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.entity.VerificationToken;
import com.personalproject.universal_pet_care.event.*;
import com.personalproject.universal_pet_care.service.email.EmailService;
import com.personalproject.universal_pet_care.service.token.VerificationTokenService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailListener {
    final EmailService emailService;
    final VerificationTokenService verificationTokenService;

    @Value("${frontend.base.url}")
    String frontendBaseUrl;

    //    begin user registration event
    @EventListener
    private void handleUserRegistrationEvent(UserRegistrationEvent event) {
        String token = generateOrRecreateToken(event.getUser());
        String verificationUrl = frontendBaseUrl + "/email-verification?token=" + token;
        sendUserRegistrationVerificationEmail(event.getUser(), verificationUrl);
    }

    private void sendUserRegistrationVerificationEmail(User user, String url) {
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
//    end user registration event

    //    begin handle appointment booked mail
    @EventListener
    private void handleAppointmentBookingEvent(AppointmentBookedEvent event) {
        User veterinarian = event.getAppointment().getRecipient();
        String url = frontendBaseUrl;
        sendAppointmentBookingEmail(veterinarian, url);
    }

    private void sendAppointmentBookingEmail(User veterinarian, String url) {
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
    @EventListener
    private void handleAppointmentApprovedEvent(AppointmentApprovedEvent event) {
        User patient = event.getAppointment().getSender();
        String url = frontendBaseUrl;
        sendAppointmentApprovedEmail(patient, url);
    }

    private void sendAppointmentApprovedEmail(User patient, String url) {
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
    @EventListener
    private void handleAppointmentDeclinedEvent(AppointmentDeclinedEvent event) {
        User patient = event.getAppointment().getSender();
        String url = frontendBaseUrl;
        sendAppointmentDeclinedEmail(patient, url);
    }

    private void sendAppointmentDeclinedEmail(User patient, String url) {
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

    //    begin handle appointment complete event
    @EventListener
    private void handleAppointmentCompleteEvent(AppointmentCompletedEvent event) {
        User patient = event.getAppointment().getSender();
        String url = frontendBaseUrl;
        sendAppointmentCompletedEmail(patient, url);
    }

    private void sendAppointmentCompletedEmail(User patient, String url) {
        String subject = "Appointment Completed Notification";
        String senderName = "Universal Pet Care Notification Service";
        String mailContent = "<p>Dear " + patient.getFirstName() + ",</p>" +
                "<p>We are pleased to inform you that your appointment has been successfully completed." +
                " Thank you for choosing our service.</p>" +
                "<p>You can view more details about your appointment by clicking the link below:</p>" +
                "<a href=\"" + url + "\">View Appointment Details</a><br/><br/>" +
                "<p>If you have any further questions or need additional assistance, feel free to reach out to us.</p>" +
                "<p>Best regards,</p>" +
                "<p><strong>Universal Pet Care Notification Service</strong></p>";
        emailService.sendEmail(patient.getEmail(), subject, senderName, mailContent);
    }
// end appointment complete event

    //    begin password reset event
    @EventListener
    private void handlePasswordResetRequestEvent(PasswordResetEvent event) {
        String token = generateOrRecreateToken(event.getUser());
        String resetUrl = frontendBaseUrl + "/reset-password?token=" + token;
        sendPasswordResetEmail(event.getUser(), resetUrl);
    }

    private void sendPasswordResetEmail(User user, String resetUrl) {
        String subject = "Password Reset Request - Universal Pet Care";
        String senderName = "Universal Pet Care";
        String mailContent = "<p>Dear " + user.getFirstName() + ",</p>" +
                "<p>We received a request to reset your password. To proceed, please click the link below:</p>" +
                "<p><a href=\"" + resetUrl + "\" style=\"color: #3498db; text-decoration: none; font-weight: bold;\">Reset Password</a></p>" +
                "<p>If you did not request this change, you can safely ignore this email. Your account remains secure.</p>" +
                "<p>Best regards,</p>" +
                "<p><strong>Universal Pet Care Team</strong></p>";

        emailService.sendEmail(user.getEmail(), subject, senderName, mailContent);
    }

    private String generateOrRecreateToken(User user) {
        if (user.getVerificationToken() != null) {
            VerificationToken verificationToken = user.getVerificationToken();
            return verificationTokenService.recreateNewVerificationToken(verificationToken.getToken());
        } else {
            return verificationTokenService.saveVerificationTokenForUser(user, UUID.randomUUID().toString());
        }
    }
}
