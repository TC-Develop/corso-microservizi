package it.large.library.gateway.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthentication implements Authentication {

    private String username;
    private final String token;
    private final List<GrantedAuthority> authorityList;
    private boolean authenticated = false;

    public JwtAuthentication(String token) {
        this.token = token;
        this.authenticated=false;
        this.authorityList=null;
    }

    public JwtAuthentication(String username, String token, List<GrantedAuthority> authorityList) {
        this.username = username;
        this.token = token;
        this.authorityList = authorityList;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Impossible change authenticated value");
    }

    @Override
    public String getName() {
        return null;
    }
}
