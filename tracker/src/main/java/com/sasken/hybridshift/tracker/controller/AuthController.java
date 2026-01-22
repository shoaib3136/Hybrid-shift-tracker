package com.sasken.hybridshift.tracker.controller;

import com.sasken.hybridshift.tracker.config.JwtUtil;
import com.sasken.hybridshift.tracker.entity.AppUser;
import com.sasken.hybridshift.tracker.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String role = user.getRoles()
                .iterator()
                .next()
                .getName()
                .replace("ROLE_", "");

        String token = jwtUtil.generateToken(user.getUsername(), role);

        return Map.of("token", token);
    }
}
