package it.large.library.gateway.security.filter;

import ch.qos.logback.core.util.ContentTypeUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class JwtLoginFilter extends OncePerRequestFilter {

    private static final String USERNAME_FIELD="username";
    private static final String PASSWORD_FIELD="password";
    private static final String LOGIN_PATH="/login";

    private AuthenticationManager authenticationManager;

    private ObjectMapper objectMapper;

    @Value("${token.duration.seconds}")
    private Long tokenDurationSeconds;

    @Value("${token.key}")
    private String tokenKey;

    public JwtLoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getServletPath().equals(LOGIN_PATH)){
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getHeader(USERNAME_FIELD),
                        request.getHeader(PASSWORD_FIELD));

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if(authentication.isAuthenticated()){
            response.getWriter().println(objectMapper.writeValueAsString(buildJwt(authentication)));
            response.setStatus(HttpStatus.OK.value());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Map<String, Object> buildJwt(Authentication authentication){
        return Map.<String, Object>of("token", JWT.create()
                .withSubject(authentication.getName())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(tokenDurationSeconds, ChronoUnit.SECONDS))
                .withClaim("roles", authoritiesToString(authentication))
                .sign(Algorithm.HMAC256(tokenKey)));
    }

    private List<String> authoritiesToString(Authentication authentication){
        return authentication.getAuthorities().stream().map(String::valueOf).toList();
    }

}
