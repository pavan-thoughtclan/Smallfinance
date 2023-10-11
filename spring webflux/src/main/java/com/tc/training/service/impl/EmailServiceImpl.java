package com.tc.training.service.impl;

import com.tc.training.exception.MyMailException;
import com.tc.training.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Mono<Void> sendEmail(String to, String subject, String body) {
         return Mono.defer(() -> {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(to);
                    message.setSubject(subject);
                    message.setText(body);
                    try {
                        mailSender.send(message);
                        return Mono.empty();
                    }
                    catch(MailException e){
                        throw new MyMailException("mail not sent");
                    }

                });

    }
}
