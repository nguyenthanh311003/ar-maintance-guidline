package com.capstone.ar_guideline.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers(
                        "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/api/v1/**")
                    .permitAll() // ✅ Allow OPTIONS requests
                    .requestMatchers(
                        "/api/v1/login", "/api/v1/register", "/api/v1/register/company")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/**")
                    .hasAnyAuthority("ADMIN", "COMPANY", "DESIGNER", "STAFF")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/**")
                    .hasAnyAuthority("ADMIN", "COMPANY", "DESIGNER", "STAFF")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/**")
                    .hasAnyAuthority("ADMIN", "COMPANY", "DESIGNER", "STAFF")
                    .anyRequest()
                    .permitAll())
        .sessionManagement(
            manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins(
            "http://localhost:3000, https://ar-maintance-guideline-ui.vercel.app") // MUST specify
        // exact origin
        .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")
        .allowedHeaders("Authorization", "Content-Type") // Allow authentication headers
        .exposedHeaders("Authorization") // Expose Authorization header
        .allowCredentials(true); // Enable cookies and authentication headers
  }
}
