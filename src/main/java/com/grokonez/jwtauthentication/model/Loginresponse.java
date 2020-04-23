package com.grokonez.jwtauthentication.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Loginresponse {
    public String username;
    public String accessToken;
    public String tokenType;
    public Set<String> authorities;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getauthorities() {
        return authorities;
    }

    public void setauthorities(Set<String> authorities) {
        this.authorities = authorities;

    }
}

