package com.sistemblog.config;

import com.sistemblog.services.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf.disable()
                )
                .authorizeHttpRequests(authz -> {
                            authz.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
                            authz.requestMatchers("/api/auth/**").permitAll();
                            authz.anyRequest().authenticated();
                        }

                )
                .exceptionHandling(exHandling ->
                        exHandling
                                .authenticationEntryPoint((request, response, authException) -> {
                                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
                                        }
                                )
                )
                .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
                .build();
    }


    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customService)
                .passwordEncoder(passwordEncoder())
                .and().build();

    }
}
