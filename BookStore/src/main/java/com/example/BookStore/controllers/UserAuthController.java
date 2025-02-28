package com.example.BookStore.controllers;

import com.example.BookStore.models.User;
import com.example.BookStore.repositories.UserRepository;
import com.example.BookStore.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Inject JWT utility

    public UserAuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            response.put("message", "Username already taken!");
            return response;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || (!user.getRole().equalsIgnoreCase("ADMIN"))) {
            user.setRole("USER");
        }

        userRepository.save(user);
        response.put("message", "User registered successfully!");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser.isPresent() && passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
            User validUser = foundUser.get();
            String token = jwtUtil.generateToken(validUser.getUsername(), "ROLE_" + validUser.getRole());

            response.put("token", token);
            response.put("userId", String.valueOf(validUser.getId())); // Include userId in the response
            return response;
        }

        response.put("message", "Invalid credentials!");
        return response;
    }
}
