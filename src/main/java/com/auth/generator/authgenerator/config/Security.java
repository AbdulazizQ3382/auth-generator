package com.auth.generator.authgenerator.config;

import com.auth.generator.authgenerator.authentication.AuthenticationEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class Security {

    Logger logger  = Logger.getLogger(Security.class.getName());
//    @Bean
//    @Order(1)
//    public void setSecurityContext(){
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication = new TestingAuthenticationToken("qannam","qannam1234","Role_Admin");
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        authentication.getName();
        Object principal = authentication.getPrincipal();
        return authentication.getAuthorities();
    }

    public void printAuthorities(){
        getAuthorities().forEach((grantedAuthority -> {
            logger.log(Level.INFO,grantedAuthority.getAuthority());
        }));
    }

    @Bean
    public SecurityFilterChain globalSecurityFilter(HttpSecurity httpSecurity, AuthorizationManager<RequestAuthorizationContext> authorizationManager) throws Exception{
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/favicon.co").permitAll()
                        .requestMatchers("/login","/register").access(authorizationManager)
                        .anyRequest().permitAll()
                );
        return httpSecurity.build();
    }

    @Bean
    AuthorizationManager<RequestAuthorizationContext> authorizationManager(){
        return ((authentication, object) -> new AuthorizationDecision(authentication.get() instanceof AuthenticationEntity));
    }
}
