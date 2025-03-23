package com.kaivix.authservice.controller;

import com.kaivix.authservice.config.JwtTokenProvider;
import com.kaivix.authservice.config.SecurityConfig;
import com.kaivix.authservice.model.User;
import com.kaivix.authservice.repository.UserRepository;
import com.kaivix.authservice.service.EmailService;
import com.kaivix.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")

@Slf4j
@RequiredArgsConstructor()
public class AuthController {

    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private UserRepository userRepository;
    private SecurityConfig securityConfig;
    private EmailService emailService;


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

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        String email = jwtTokenProvider.getUsernameFromToken(token);
        User user = userService.getUserByEmail(email);
        user.setIsEnabled(true);
        emailService.sendVerificationToken(user.getEmail());

        return ResponseEntity.ok("Account is verified");
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