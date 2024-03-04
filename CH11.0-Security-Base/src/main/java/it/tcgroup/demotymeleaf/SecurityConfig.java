package it.tcgroup.demotymeleaf;


import it.tcgroup.demotymeleaf.object.WebUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {



    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(loginConfigurer -> {
            try {
                loginConfigurer.init(http);
                loginConfigurer.permitAll();
                loginConfigurer.loginPage("/login");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        http.logout(logoutConfigurer -> {
            try {
                logoutConfigurer.logoutUrl("/logout")
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/")
                        .configure(http);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        http.httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login", "/img/spring-security.svg").permitAll()
                            .requestMatchers("/**").authenticated();
                });
        return http.build();
    }

    @Bean
    protected List<UserDetails> userList(){
        List<UserDetails> userList = new ArrayList<>();
        userList.add(new WebUser("admin", "admin",List.of(new SimpleGrantedAuthority("admin"))));
        userList.add(new WebUser("user", "user",List.of(new SimpleGrantedAuthority("user"))));
        userList.add(new WebUser("guest", "guest",List.of(new SimpleGrantedAuthority("guest"))));
        return userList;
    }


    @Bean
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(userList());
        return userDetailsManager;
    }


    @Bean
    protected PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
