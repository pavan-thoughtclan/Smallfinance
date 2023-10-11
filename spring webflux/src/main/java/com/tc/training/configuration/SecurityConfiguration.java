package com.tc.training.configuration;

import com.tc.training.repo.UserRepository;
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
//@EnableGlobalMethodSecurity(prePostEnabled = true)
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
            String email = (String) jwt.getClaims().get("email");
            System.out.println("hello");
            System.out.println(email);

            // Load user details from the userDetailsService in a reactive way
            return userRepository.findByEmail(email)
                    .flatMapMany(user -> {
                        Flux<GrantedAuthority> authorities1 = Flux.just(new SimpleGrantedAuthority(user.getRoleName().toString()));
                        authorities1.subscribe(System.out::println);
                        Flux<GrantedAuthority> newFlux = Flux.concat(authoritiesFlux, authorities1);
//                        newFlux.subscribe(System.out::println);
                        return authorities1;
                    })
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with username: " + email)));
        });
        return converter;
    }





}
