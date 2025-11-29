package com.restoflow.service;

import com.restoflow.domain.user.Admin;
import com.restoflow.domain.user.Customer;
import com.restoflow.domain.user.User;
import com.restoflow.dto.LoginRequestDTO;
import com.restoflow.dto.RegisterRequestDTO;
import com.restoflow.enums.AdminRole;
import com.restoflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(RegisterRequestDTO request) {
        if (userRepository.findAll().stream().anyMatch(u -> u.getName().equals(request.getUsername()))) {
            throw new RuntimeException("Username already exists");
        }

        String hashedPassword = hashPassword(request.getPassword());

        User user;
        if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            user = new Admin(request.getUsername(), hashedPassword, AdminRole.RESTAURANT_ADMIN);
        } else if ("INVENTORY_ADMIN".equalsIgnoreCase(request.getRole())) {
            user = new Admin(request.getUsername(), hashedPassword, AdminRole.INVENTORY_ADMIN);
        } else {
            user = new Customer(request.getUsername(), hashedPassword);
        }

        return userRepository.save(user);
    }

    public User login(LoginRequestDTO request) {
        String hashedPassword = hashPassword(request.getPassword());
        Optional<User> user = userRepository.findAll().stream()
                .filter(u -> u.getName().equals(request.getUsername()) && u.getPassword().equals(hashedPassword))
                .findFirst();

        return user.orElseThrow(() -> new RuntimeException("Invalid username or password"));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
