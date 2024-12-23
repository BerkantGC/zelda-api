package com.gc.zelda_api.service.auth;

import com.gc.zelda_api.dto.auth.LoginRequest;
import com.gc.zelda_api.dto.auth.LoginResponse;
import com.gc.zelda_api.dto.auth.RegisterRequest;
import com.gc.zelda_api.model.User;
import com.gc.zelda_api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(JwtService jwtService,
                       UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User authenticate(LoginRequest loginRequest) {
        User user;
        String username = loginRequest.getUsername();
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (username != null){
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "User not found"
                    ));
        } else if (email != null){
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "User not found"
                    ));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User not found"
                    );
        }

        if ( !passwordEncoder.matches(password, user.getPassword()) ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );


        return user;
    }


    public void register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirst_name());
        user.setLastName(request.getLast_name());

        userRepository.save(user);
    }


    public User refreshUser(String refreshToken) {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!jwtService.isRefreshTokenValid(refreshToken, details)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);

        return userRepository.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    public LoginResponse getResponseWithNewCredentials(User user){
        String newJwtToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder()
                .username(user.getUsername())
                .access_token(newJwtToken)
                .access_token_expires_at(
                        System.currentTimeMillis() + jwtService.getExpirationTime()
                )
                .refresh_token(newRefreshToken)
                .refresh_token_expires_at(
                        System.currentTimeMillis() + jwtService.getRefreshExpirationTime()
                )
                .build();
    }
}
