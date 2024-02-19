package it.large.library.catalogue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

// @Configuration: Indica che questa classe è una classe di configurazione per Spring.
@Configuration
// @EnableMethodSecurity: Abilita il supporto per l'annotazione di sicurezza nei metodi.
// I tre parametri specificano l'abilitazione di diversi stili di annotazione di sicurezza:
//      prePostEnabled = true: Abilita le annotazioni @PreAuthorize e @PostAuthorize.
//      securedEnabled = true: Abilita l'uso dell'annotazione @Secured.
//      jsr250Enabled = true: Abilita l'uso di annotazioni JSR-250.
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
// @EnableWebSecurity: Indica che questa classe fornisce configurazioni di sicurezza per un'applicazione basata su web.
@EnableWebSecurity
public class SecurityConfig {

    /*
     * Questo metodo configura la catena di filtri di sicurezza per la tua applicazione. Viene chiamato automaticamente da Spring. La configurazione include:
     *      Disabilitazione del login basato su form (http.formLogin(...))
     *      Impostazione della gestione delle sessioni come stateless (http.sessionManagement(...))
     *      Configurazione dell'autenticazione di base (http.httpBasic(...))
     *      Autorizzazione di tutte le richieste autenticate (authorize.anyRequest().authenticated())
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basciAuthConfigurer -> basciAuthConfigurer.init(http))
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated();
                });

        return http.build();
    }

    /*
     * Questo metodo crea un servizio di dettagli utente in memoria.
     * Crea e restituisce un oggetto InMemoryUserDetailsManager con tre utenti preconfigurati:
     *      admin, user e guest, ciascuno con ruoli specificati attraverso le autorità (SimpleGrantedAuthority).
     */
    @Bean
    public UserDetailsService userDetailsService(){
        User admin = new User("admin", "admin", List.of(new SimpleGrantedAuthority("ADMIN")));
        User user = new User("user", "user", List.of(new SimpleGrantedAuthority("USER")));
        User guest = new User("guest", "guest", List.of(new SimpleGrantedAuthority("GUEST")));
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(List.of(admin, user, guest));
        return userDetailsManager;
    }

    /*
     * Configura un PasswordEncoder.
     * In questo caso, viene utilizzato NoOpPasswordEncoder, che è un'implementazione di PasswordEncoder che non effettua alcuna crittografia (non sicura e utilizzata solo per scopi di esempio).
     * In un'applicazione reale, è consigliabile utilizzare un PasswordEncoder che esegue una crittografia sicura come BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder encoder(){
        return NoOpPasswordEncoder.getInstance();
    }


}