package com.smallfinance.services;

public interface MailService {
    void sendEmail(String to, String subject, String body);
}
