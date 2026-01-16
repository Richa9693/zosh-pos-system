package com.zosh.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource
    ) throws Exception {

        return http
                // ✅ Stateless session (JWT)
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ✅ Authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/super-admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )

                // ✅ JWT Filter
                .addFilterBefore(
                        new JwtValidator(),
                        BasicAuthenticationFilter.class
                )

                // ✅ CSRF disable
                .csrf(AbstractHttpConfigurer::disable)

                // ✅ CORS config
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource)
                )

                .build();
    }

    // ✅ CORS Configuration

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(
                        Arrays.asList(
                                "http:localhost:5173",
                                "http://localhost:3000"
                        )
                );
                cfg.addAllowedOriginPattern("*");
                cfg.addAllowedHeader("*");
                cfg.addAllowedMethod("*");
                cfg.setAllowCredentials(true);
                return cfg;
            }
        };
    }
}
