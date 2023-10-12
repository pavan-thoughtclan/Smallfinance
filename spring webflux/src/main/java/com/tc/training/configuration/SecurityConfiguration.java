package com.tc.training.configuration;

import com.tc.training.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfiguration{

    @Autowired
    private UserRepository userRepository;

        @Bean
        public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {

            http.csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.disable())
                    .authorizeExchange(auth -> auth
                            .pathMatchers(
                                    "/account/{id}",
                                    "/account/getBalance/{id}",
                                    "/account/get_account_by_user",
                                    "/account/home_page",
                                    "/account/set_kyc",
                                    "/loan/**",
                                    "/deposit/**",
                                    "/fd/**",
                                    "/rd/**",
                                    "/transaction/**",
                                    "/users/**",
                                    "/slabs/**"
                            ).authenticated()
                            .pathMatchers(
                                    "/account/create"

                            ).permitAll()
                            .anyExchange().authenticated()
                    )
                    .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> jwt
                                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                            )
                    );
            return  http.build();

        }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        ReactiveJwtGrantedAuthoritiesConverterAdapter reactiveAdapter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(authoritiesConverter);
        converter.setJwtGrantedAuthoritiesConverter(reactiveAdapter);
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Flux<GrantedAuthority> authoritiesFlux = reactiveAdapter.convert(jwt);
            String role = (String) jwt.getClaims().get("roles");
            System.out.println(role );
            Flux<GrantedAuthority> authorities1 = Flux.just(new SimpleGrantedAuthority(role));
            return authorities1;
        });
        return converter;
    }





}
