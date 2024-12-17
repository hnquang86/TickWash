package com.purewash.configuration.Security;

import com.purewash.filters.JwtTokenFilter;
import com.purewash.services.RefreshToken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final RefreshTokenService tokenService;
    private final JWTDecoder customJwtDecoder;
    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    private final String[] PUBLIC_ENDPOINTS = {

    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    "/v3/api-docs/**", "/swagger-ui/index.html", "/swagger-ui/**", "/swagger-ui.html", "/swagger/**",
                                    "/api-docs/**", "/swagger-resources/**", "/swagger-resources/", "/configuration/ui", "/configuration/security",

                                    //Auth
                                    String.format("%s/auth/register", apiPrefix),
                                    String.format("%s/auth/login", apiPrefix),
                                    String.format("%s/auth/refreshtoken", apiPrefix),
                                    String.format("%s/auth/me", apiPrefix),
                                    String.format("%s/auth/avatar", apiPrefix),
                                    String.format("%s/auth/change-password", apiPrefix),
                                    String.format("%s/auth/update-info", apiPrefix),

                                    //Role
                                    String.format("%s/roles", apiPrefix),
                                    String.format("%s/**", apiPrefix),

                                    // Users
                                    String.format("%s/users", apiPrefix)
                            )
                            .permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .logout(logout -> logout
                        .logoutUrl(String.format("%s/auth/logout", apiPrefix))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler(tokenService))
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


}