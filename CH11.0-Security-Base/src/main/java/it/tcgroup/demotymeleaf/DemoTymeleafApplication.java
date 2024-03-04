package it.tcgroup.demotymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class DemoTymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTymeleafApplication.class, args);
    }

}
