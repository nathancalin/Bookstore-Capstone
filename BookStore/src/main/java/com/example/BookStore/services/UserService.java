package com.example.BookStore.services;

import com.example.BookStore.models.User;
import com.example.BookStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject password encoder

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(int id, User userDetails, String authenticatedUsername) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            // Check if the authenticated user is updating their own profile or is an admin
            if (!existingUser.getUsername().equals(authenticatedUsername) && !"ADMIN".equals(authenticatedUsername)) {
                throw new RuntimeException("Unauthorized to update this user!");
            }

            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setUsername(userDetails.getUsername());

            // Only update the password if it's provided, and encrypt it
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }

            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(int id, String authenticatedUsername) {
        User userToDelete = userRepository.findById(id).orElse(null);

        if (userToDelete != null) {
            // Only allow self-delete or admin delete
            if (!userToDelete.getUsername().equals(authenticatedUsername) && !"ADMIN".equals(authenticatedUsername)) {
                throw new RuntimeException("Unauthorized to delete this user!");
            }
            userRepository.deleteById(id);
        }
    }
}
