package org.example.ticketservice.security;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@KeycloakConfiguration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    JwtAuthConverter jwtAuthConverter;

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Более современный способ отключения CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/TMA/api/v2/tickets/**").authenticated()
                        .anyRequest().permitAll()
                )
//                .oauth2ResourceServer(t -> t.jwt(Customizer.withDefaults()))
                .oauth2ResourceServer(t -> {
                    t.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter));
                })
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Используйте ВАШ CorsConfigurationSource

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // ЯВНО укажите разрешенный origin.  "*" может не работать с credentials.
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Явно укажите методы
        configuration.setAllowedHeaders(List.of("Content-Type", "Authorization", "Accept", "X-Requested-With")); // Явно укажите заголовки
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}