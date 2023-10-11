package com.tc.training.service;


import reactor.core.publisher.Mono;

public interface EmailService {

    public Mono<Void> sendEmail(String to, String subject, String body);
}
