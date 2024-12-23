package com.gc.zelda_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gc.zelda_api.service.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No token present, continue filter chain
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract the JWT token from the Authorization header
            final String jwt = extractJwt(authHeader);
            final String username = jwtService.extractUsername(jwt);

            // Check if the token is valid and if no authentication exists yet
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticateUser(username, jwt, request);
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("error", exception.getMessage());
            errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            // Convert the map to JSON using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorDetails);

            // Write the JSON response
            response.getWriter().write(jsonResponse);
        }
    }

    /**
     * Extracts JWT token from the Authorization header.
     */
    private String extractJwt(String authHeader) {
        return authHeader.substring(7); // Removing "Bearer " prefix
    }

    /**
     * Authenticates the user if the token is valid.
     */
    private void authenticateUser(String username, String jwt, HttpServletRequest request) {
        try {
            // Load user details based on the username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // If the token is valid, create an authentication token
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No credentials needed as it's a JWT token
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                logger.warn("Invalid JWT token for user: {}", username);
            }
        } catch (Exception e) {
            logger.error("Authentication failed for user: {} - {}", username, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); // Re-throw exception to be handled by global exception handler
        }
    }
}
