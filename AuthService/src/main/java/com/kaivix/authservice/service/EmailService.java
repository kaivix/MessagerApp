package com.kaivix.authservice.service;

import com.kaivix.authservice.config.JwtTokenProvider;
import com.kaivix.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    private JavaMailSender mailSender;
    private JwtTokenProvider jwtTokenProvider;

    public void sendVerificationToken(String to) {
        String token = jwtTokenProvider.generateVerificationToken(to);
        String url = "https://authservice.kaivix.com/auth/confirm?token=" + token;
        String message = "Перейдите по ссылке для подтверждения:" + url;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(message);
        mailSender.send(email);
    }
}
