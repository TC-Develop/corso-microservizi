package it.large.library.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.large.library.gateway.security.filter.JwtLoginFilter;
import it.large.library.gateway.security.filter.JwtVerifyFilter;
import it.large.library.gateway.security.provider.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

/*
    @Autowired
    private AuthenticationConfiguration authConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
*/

    @Bean
    public JwtLoginFilter jwtLoginFilter(@Autowired AuthenticationManager authenticationManager,
                                         @Autowired ObjectMapper objectMapper){
        return new JwtLoginFilter(authenticationManager, objectMapper);
    }
    @Bean
    public JwtVerifyFilter jwtVerifyFilter(@Autowired AuthenticationManager authenticationManager){
        return new JwtVerifyFilter(authenticationManager);
    }

    public List<UserDetails> userList =
            List.of(
                    new User("user", "user", List.of(new SimpleGrantedAuthority("user"))),
                    new User("admin","admin", List.of(new SimpleGrantedAuthority("admin")))
            );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, @Autowired JwtLoginFilter jwtLoginFilter,
                                           @Autowired JwtVerifyFilter jwtVerifyFilter) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/version").permitAll()
                    .requestMatchers("/**").authenticated();
        })
                .addFilterBefore(jwtLoginFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtVerifyFilter, JwtLoginFilter.class);
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


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            @Autowired PasswordEncoder passwordEncoder,
            @Autowired InMemoryUserDetailsManager userDetailsManager
    ){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder);
        provider.setUserDetailsService(userDetailsManager);
        return provider;
    }


    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(@Autowired ObjectMapper objectMapper){
        return new JwtAuthenticationProvider(objectMapper);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             @Autowired JwtAuthenticationProvider jwtAuthenticationProvider,
                                             @Autowired DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }
}
