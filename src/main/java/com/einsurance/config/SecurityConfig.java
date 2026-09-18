package com.einsurance.config;

import com.einsurance.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
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
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Public APIs (Authentication & Registration)
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/customers/register",
                                "/api/agents/register",
                                "/api/employees/register"
                        ).permitAll()

                        // Admin initial creation
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/admins"
                        ).permitAll()

                        // Publicly viewable insurance plans and schemes
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/plans/**",
                                "/api/schemes/**"
                        ).permitAll()

                        // Premium calculator can be accessed publicly or by logged in users
                        .requestMatchers(
                                "/api/policies/calculate-premium"
                        ).permitAll()

                        // Admin protected APIs
                        .requestMatchers(
                                "/api/admins/**",
                                "/api/banks/**"
                        ).hasRole("ADMIN")

                        // Customer purchase & policies
                        .requestMatchers(
                                "/api/policies/purchase",
                                "/api/policies/my-policies",
                                "/api/payments/my-payments"
                        ).hasAnyRole("CUSTOMER", "ADMIN")

                        // All other APIs require authentication
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}