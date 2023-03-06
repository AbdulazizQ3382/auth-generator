package com.auth.generator.authgenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class Security {

    Logger logger  = Logger.getLogger(Security.class.getName());
    @Bean
    @Order(1)
    public void setSecurityContext(){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new TestingAuthenticationToken("qannam","qannam1234","Role_Admin");
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Bean
    @Order(2)
    public Collection<? extends GrantedAuthority> getAuthorities(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        authentication.getName();
        Object principal = authentication.getPrincipal();
        return authentication.getAuthorities();
    }

    @Bean
    @Order(3)
    public void printAuthorities(){
        getAuthorities().forEach((grantedAuthority -> {
            logger.log(Level.INFO,grantedAuthority.getAuthority());
        }));
    }
}
