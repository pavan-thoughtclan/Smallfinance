//package com.microFinance1.configuration;
//
//import com.microFinance1.repository.UserRepository;
//import io.micronaut.context.event.ApplicationEventPublisher;
//import io.micronaut.http.HttpRequest;
//import io.micronaut.security.authentication.Authentication;
//import io.micronaut.security.token.TokenAuthenticationFetcher;
//import io.micronaut.security.token.reader.TokenResolver;
//import io.micronaut.security.token.validator.TokenValidator;
//import jakarta.inject.Inject;
//import org.reactivestreams.Publisher;
//
//import java.util.Collection;
//
//public class SecurityConfig extends TokenAuthenticationFetcher {
//    @Inject
//    private UserRepository userRepository;
//
//    public SecurityConfig(Collection<TokenValidator> tokenValidators, TokenResolver tokenResolver, ApplicationEventPublisher eventPublisher) {
//        super(tokenValidators, tokenResolver, eventPublisher);
//    }
//
//    @Override
//    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
//        return null;
//    }
//}
