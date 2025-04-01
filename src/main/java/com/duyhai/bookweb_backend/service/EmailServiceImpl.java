package com.duyhai.bookweb_backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMessage(String from, String to, String subject, String text) {
       // MimeMailMessage mimeMailMessage = new MimeMailMessage(); -> gui co dinh kem file
        //SimpleMailMessage message = new SimpleMailMessage(); -> gui mail vs simple message
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
//            helper.addAttachment();
        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        //gui email
        mailSender.send(message);
    }
}
