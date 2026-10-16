package org.example.usermanagementrestapi.service;

import org.example.usermanagementrestapi.dto.UserRequest;
import org.example.usermanagementrestapi.dto.UserResponse;
import org.example.usermanagementrestapi.model.User;
import org.example.usermanagementrestapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Dependency Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {
        User user = new User(null, request.getName(), request.getEmail());
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository
                .findById(id)
                .map(this::mapToResponse);
    }

    public Optional<UserResponse> updateUser(Long id, UserRequest request) {
        return userRepository
                .findById(id)
                .map(existingUser -> {
            existingUser.setName(request.getName());
            existingUser.setEmail(request.getEmail());
            User updatedUser = userRepository.save(existingUser);
            return mapToResponse(updatedUser);
        });
    }

    public boolean deleteUser(Long id) {
        return userRepository
                .deleteById(id);
    }

    public List<UserResponse> searchUsers(String query) {
        return userRepository.searchByName(query).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Helper method to convert User model to UserResponse DTO
    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}