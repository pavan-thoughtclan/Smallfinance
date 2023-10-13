package com.tc.training.smallfinance.configuration;

import com.tc.training.smallfinance.model.User;
import com.tc.training.smallfinance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    UserRepository userRepository;

//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuerUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/accounts/{id}",
                                "/accounts/getBalance",
                                "/accounts/getAccountByUser",
                                "/accounts/homePage",
                                "/accounts/setKyc",
                                "/loans/**",
                                "/deposits/**",
                                "/fds/**",
                                "/rds/**",
                                "/transactions/**",
                                "/users/**",
                                "/slabs/**"
                        ).authenticated()
                        .requestMatchers(
                                "/accounts"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
                return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String role = (String) jwt.getClaims().get("roles");
            List<SimpleGrantedAuthority> userAuthorities = new ArrayList<>();
            userAuthorities.add(new SimpleGrantedAuthority(role));
            return  (List<GrantedAuthority>) (List<?>)userAuthorities;
        });
        return converter;
    }

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
//
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            Collection<GrantedAuthority> authorities = authoritiesConverter.convert(jwt);
//            System.out.println("User Roles: " + authorities);
//
//            // Extract the email claim from the JWT
//            String email = (String) jwt.getClaims().get("email");
//            System.out.println("User Email: " + email);
//
//            // You can access the roles directly from the authorities
//            List<SimpleGrantedAuthority> userAuthorities = authorities.stream()
//                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
//                    .collect(Collectors.toList());
//
//            System.out.println("User Authorities: " + userAuthorities);
//
//            return userAuthorities;
//        });
//
//        return converter;
//    }

}

