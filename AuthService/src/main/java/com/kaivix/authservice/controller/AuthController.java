package com.kaivix.authservice.controller;

import com.kaivix.authservice.config.JwtTokenProvider;
import com.kaivix.authservice.config.SecurityConfig;
import com.kaivix.authservice.model.User;
import com.kaivix.authservice.repository.UserRepository;
import com.kaivix.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")

@Slf4j
public class AuthController {
    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, AuthenticationManager authenticationManager
    , UserService userService, UserRepository userRepository, SecurityConfig securityConfig) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private UserRepository userRepository;
    private SecurityConfig securityConfig;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Создаём объект Authentication для аутентификации
        User user = userService.getUserByEmail(loginDTO.getEmail());
        // Генерируем JWT токен
        String token = jwtTokenProvider.generateToken(user);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/reg")
    public void register(@RequestBody AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User newUser = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(securityConfig.passwordEncoder().encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(newUser);
        log.info("User created: {}", newUser);
    }

}


@RequiredArgsConstructor
class LoginDTO{
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}

@RequiredArgsConstructor
class AuthRequest {
    private String login;
    private String email;
    private String password;

    public String getLogin() {
        return login;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}