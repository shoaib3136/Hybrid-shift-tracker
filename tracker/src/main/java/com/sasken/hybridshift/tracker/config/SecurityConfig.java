package com.sasken.hybridshift.tracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity   // ✅ REQUIRED for @PreAuthorize
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // 🔓 PUBLIC
                        .requestMatchers("/auth/**").permitAll()

                        // 👤 EMPLOYEES API
                        .requestMatchers(HttpMethod.GET, "/employees/**")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/employees/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/employees/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/employees/**")
                        .hasRole("ADMIN")

                        // 🕒 WORKLOGS → controlled by @PreAuthorize
                        .requestMatchers("/worklogs/**")
                        .authenticated()

                                .requestMatchers(HttpMethod.GET, "/shifts/me")
                                .hasAnyRole("USER", "ADMIN")

// ADMIN only
                                .requestMatchers("/shifts/**")
                                .hasRole("ADMIN")

                        // ❗ Anything else
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
