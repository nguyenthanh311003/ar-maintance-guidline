package com.capstone.ar_guideline.configurations;

import com.amazonaws.services.lightsail.model.UnauthenticatedException;
import com.capstone.ar_guideline.services.IJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final IJWTService jwtService;

  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

    if (StringUtils.isEmpty(authHeader)
        || !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer")) {
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUserName(jwt);
    try {
      if (!StringUtils.isEmpty(userEmail)
          && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

        if (jwtService.isTokenValid(jwt, userDetails)) {
          SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

          UsernamePasswordAuthenticationToken token =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());

          token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          securityContext.setAuthentication(token);
          SecurityContextHolder.setContext(securityContext);
        }
      }
      filterChain.doFilter(request, response);
    } catch (Exception baseException) {
      throw new UnauthenticatedException("Invalid Token");
    }
  }
}
