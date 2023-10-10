//package com.microFinance1.services.impls;
//
//import com.microFinance1.services.EmailService;
//import jakarta.inject.Inject;
//import jakarta.inject.Singleton;
//
//@Singleton
//public class EmailServiceImpl implements EmailService {
//    @Inject
//    private EmailSender emailSender;
//
//    public EmailServiceImpl(EmailSender emailSender) {
//        this.emailSender = emailSender;
//    }
//    @Override
//    public void sendEmail(String to, String subject, String body) {
//        try{
//            emailSender.send(Email.builder()
//                    .from("anvithckdvg@gmail.com")
//                    .to(to)
//                    .subject(subject)
//                    .body(body));
//        }
//        catch(EmailException exception) {
//            throw new EmailException("mail not sent") ;
//        }
//    }
//}
