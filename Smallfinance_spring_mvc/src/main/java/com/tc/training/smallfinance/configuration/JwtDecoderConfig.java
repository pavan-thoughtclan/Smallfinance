//package com.tc.training.smallfinance.configuration;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
//import org.springframework.security.oauth2.core.OAuth2TokenValidator;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtDecoders;
//import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
//import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import java.time.Duration;
//
//@Configuration
//public class JwtDecoderConfig {
//
//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuerUri;
//
//    @Bean
//    JwtDecoder jwtDecoder() {
//        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
//                JwtDecoders.fromIssuerLocation(issuerUri);
//
//        OAuth2TokenValidator<Jwt> withClockSkew = new DelegatingOAuth2TokenValidator<>(
//                new JwtTimestampValidator(Duration.ofSeconds(60)),
//                new JwtIssuerValidator(issuerUri));
//
//        jwtDecoder.setJwtValidator(withClockSkew);
//
//        return jwtDecoder;
//    }
//}
