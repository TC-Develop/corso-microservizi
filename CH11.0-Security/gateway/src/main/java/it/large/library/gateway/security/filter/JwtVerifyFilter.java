package it.large.library.gateway.security.filter;

import it.large.library.gateway.security.authentication.JwtAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtVerifyFilter extends OncePerRequestFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }
        token = token.replaceFirst(TOKEN_PREFIX,"");
        JwtAuthentication jwtAuthentication = new JwtAuthentication(token);

        Authentication authentication = authenticationManager.authenticate(jwtAuthentication);

        if(authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
