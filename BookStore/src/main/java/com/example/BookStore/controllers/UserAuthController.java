package com.example.BookStore.controllers;

import com.example.BookStore.models.User;
import com.example.BookStore.repositories.UserRepository;
import com.example.BookStore.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public String registerUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            return "Username already taken!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password

        // Ensure roles are assigned properly
        if (user.getRole() == null || (!user.getRole().equalsIgnoreCase("ADMIN"))) {
            user.setRole("USER"); // Default role
        }

        userRepository.save(user);
        return "User registered successfully!";
    }


    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser.isPresent() && passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
            String token = jwtUtil.generateToken(foundUser.get().getUsername(), "ROLE_" + foundUser.get().getRole()); // Ensure ROLE_ADMIN
            return "Bearer " + token; // Return token
        }
        return "Invalid credentials!";
    }
}
