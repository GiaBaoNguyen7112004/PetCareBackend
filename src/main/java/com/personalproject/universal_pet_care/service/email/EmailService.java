package com.personalproject.universal_pet_care.service.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailService {
//    Bean nay duoc tu dong tao trong application.yml khi su dung dependency spring-boot-starter-mail
    final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String username;

    public void sendEmail(String to, String subject, String senderName, String content) throws MessagingException,
            UnsupportedEncodingException {
//        MimeMessage la email ho tro html, dinh kem file
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage);
        mimeMessageHelper.setFrom(username, senderName);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);

        mailSender.send(mimeMailMessage);
    }
}
