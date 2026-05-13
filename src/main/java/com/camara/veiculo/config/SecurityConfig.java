package com.camara.veiculo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET,  "/api/veiculos").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/agendamentos/disponibilidade").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/agendamentos/futuros").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/formularios").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/agendamentos").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/agendamentos/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/api/admin/**").authenticated()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(
            @Value("${app.admin.username}") String username,
            @Value("${app.admin.password}") String password) {
        UserDetails admin = User.withDefaultPasswordEncoder()
            .username(username)
            .password(password)
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(admin);
    }
}