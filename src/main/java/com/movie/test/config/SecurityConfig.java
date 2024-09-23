package com.movie.test.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean PasswordEncoder passwordEncoder() {
        log.info("PasswordEncoder()");
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("SecurityFilterChain()");

        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable());

        http
                .formLogin(login -> login.disable());

        return http.build();
    }
}
