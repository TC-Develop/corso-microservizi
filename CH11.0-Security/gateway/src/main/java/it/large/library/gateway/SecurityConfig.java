package it.large.library.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.large.library.gateway.security.filter.JwtLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtLoginFilter jwtLoginFilter(@Autowired AuthenticationManager authenticationManager,
                                         @Autowired ObjectMapper objectMapper){
        return new JwtLoginFilter(authenticationManager, objectMapper);
    }

    public List<UserDetails> userList =
            List.of(
                    new User("user", "user", List.of(new SimpleGrantedAuthority("user"))),
                    new User("admin","admin", List.of(new SimpleGrantedAuthority("admin")))
            );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, @Autowired JwtLoginFilter jwtLoginFilter) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/version").permitAll()
                    .requestMatchers("/**").authenticated();
        })
                .addFilterBefore(jwtLoginFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager detailsManager(){
        return  new InMemoryUserDetailsManager(userList);
    }


    @Bean
    public PasswordEncoder PasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
