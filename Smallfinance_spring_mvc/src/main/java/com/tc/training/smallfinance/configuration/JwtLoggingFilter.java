//package com.tc.training.smallfinance.configuration;
//
//import org.springframework.core.annotation.Order;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Order(1)
//public class JwtLoggingFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
//        try {
//            // Get the JWT token from the SecurityContext
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (principal instanceof Jwt) {
//                Jwt jwt = (Jwt) principal;
//                // Log the decoded JWT token and its claims
//                System.out.println("Decoded JWT Token: " + jwt.getTokenValue());
//                System.out.println("Subject: " + jwt.getSubject());
//                System.out.println("Decoded JWT Token Values:");
//
//                // Print all claims
//                jwt.getClaims().forEach((key, value) -> {
//                    System.out.println(key + ": " + value);
//                });
//                // Add more claim logging here as needed
//            }
//        } catch (Exception e) {
//            // Handle exceptions if necessary
//        }
//
//        // Continue with the request
//        filterChain.doFilter(request, response);
//    }
//
//
//}
