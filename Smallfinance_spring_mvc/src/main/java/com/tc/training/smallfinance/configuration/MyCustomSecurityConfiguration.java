//package com.tc.training.smallfinance.configuration;
//
//import com.tc.training.smallfinance.model.User;
//import com.tc.training.smallfinance.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
//import org.springframework.security.oauth2.core.OAuth2TokenValidator;
//import org.springframework.security.oauth2.jwt.*;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableWebSecurity
//public class MyCustomSecurityConfiguration {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuerUri;
//
//    @Bean
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorize -> authorize
////                        .requestMatchers("/account/get_balance").hasAuthority("SCOPE_message:read")
//                                .antMatchers("/create").permitAll() // Public endpoints accessible to all
//                                .antMatchers("/account/set_kyc").hasRole("ROLE_MANAGER") // Role-based access control
//                                .antMatchers("/account/get_balance").hasRole("CUSTOMER") // Role-based access control
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
//                        )
//                );
//        return http.build();
//    }
//
////    @Bean
////    public JwtAuthenticationConverter jwtAuthenticationConverter() {
////        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
////        OAuth2TokenValidator<Jwt> withClockSkew = new DelegatingOAuth2TokenValidator<>(
////                new JwtTimestampValidator(Duration.ofSeconds(60)),
////                new JwtIssuerValidator(issuerUri)
////        );
////        converter.setJwtGrantedAuthoritiesConverter((Converter<Jwt, Collection<GrantedAuthority>>) withClockSkew);
////        return converter;
////    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//
//        // Convert "roles" claim to authorities with the "ROLE_" prefix
//        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        authoritiesConverter.setAuthoritiesClaimName("roles");
//        authoritiesConverter.setAuthorityPrefix("ROLE_");
//
//        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
//        // Log the JWT token and its claims
//
//        System.out.println("jwtauth");
//
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            Collection<GrantedAuthority> authorities = authoritiesConverter.convert(jwt);
//            System.out.println("User Roles: " + authorities);
//            // Extract the email claim from the JWT
//            String email = (String) jwt.getClaims().get("email");
//            System.out.println("User Email: " + email);
//            User user = userRepository.findByEmail(email);
//            if (user == null) {
//                throw new UsernameNotFoundException("User not found with username: " + email);
//            }
//
//            // Use the roleName field directly, as it's already of type Role
//            List<SimpleGrantedAuthority> userAuthorities = new ArrayList<>();
//            userAuthorities.add(new SimpleGrantedAuthority(user.getRoleName().name()));
//            System.out.println(userAuthorities);
//
//            return (List<GrantedAuthority>) (List<?>) userAuthorities;
//        });
//
//
//
//        return converter;
//    }
//
//
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
//
//
//
//
//}
//
