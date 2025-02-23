package com.capstone.ar_guideline.configurations;

import com.capstone.ar_guideline.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.payos.PayOS;

@Configuration
@RequiredArgsConstructor
@Data
public class AppConfig {

  @Value("${application.url}")
  private String applicationUrl; // REMOVE STATIC

  @Value("${PAYOS_CLIENT_ID}")
  private String clientId;
  @Value("${PAYOS_API_KEY}")
  private String apiKey;
  @Value("${PAYOS_CHECKSUM_KEY}")
  private String checksumKey;

  private final UserRepository userRepository;

  public String getApplicationUrl() { // REMOVE STATIC
    return applicationUrl;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return userEmail ->
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + userEmail));
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public PayOS payOS() {
    return new PayOS(clientId, apiKey, checksumKey);
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
