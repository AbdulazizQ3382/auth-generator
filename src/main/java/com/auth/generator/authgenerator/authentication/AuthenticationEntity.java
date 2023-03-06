package com.auth.generator.authgenerator.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationEntity extends AbstractAuthenticationToken {

    private final Authentication authentication;
    public AuthenticationEntity(Authentication authentication) {
        super(authentication.getAuthorities());
        this.authentication = authentication;
    }

    @Override
    public Object getCredentials() {
        return this.authentication.getCredentials();
    }

    @Override
    public Object getPrincipal() {
        return this.authentication.getPrincipal();
    }

}
