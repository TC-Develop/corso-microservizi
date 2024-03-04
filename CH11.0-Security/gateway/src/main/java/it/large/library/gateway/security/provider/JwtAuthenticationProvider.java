package it.large.library.gateway.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.large.library.gateway.security.authentication.JwtAuthentication;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class JwtAuthenticationProvider implements AuthenticationProvider {
    private ObjectMapper objectMapper;

    public JwtAuthenticationProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getCredentials();
        DecodedJWT decodedJWT = JWT.decode(token);
        System.out.println(decodedJWT.getPayload());

        if(Instant.now().isAfter(decodedJWT.getExpiresAtAsInstant())){
            throw new AccountExpiredException("Token Expired");
        }
        List<GrantedAuthority> grantedAuthorities =
                buldGrantedAuthorityList(decodedJWT.getClaim("roles").asList(String.class));

        JwtAuthentication jwtAuthentication = new JwtAuthentication(decodedJWT.getSubject(),token, grantedAuthorities);

        return jwtAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthentication.class);
    }

    private List<GrantedAuthority> buldGrantedAuthorityList(List<String> roles){
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for(String role : roles){
            grantedAuthorityList.add(new SimpleGrantedAuthority(role));
        }
        return grantedAuthorityList;
    }
}
