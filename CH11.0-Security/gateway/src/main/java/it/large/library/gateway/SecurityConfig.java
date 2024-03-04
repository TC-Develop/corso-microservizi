package it.large.library.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    public List<UserDetails> userList =
            List.of(
                    new User("user", "user", List.of(new SimpleGrantedAuthority("user"))),
                    new User("admin","admin", List.of(new SimpleGrantedAuthority("admin")))
            );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/version").permitAll()
                    .requestMatchers("/**").authenticated();
        });
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager detailsManager(){
        return  new InMemoryUserDetailsManager(userList);
    }


}
