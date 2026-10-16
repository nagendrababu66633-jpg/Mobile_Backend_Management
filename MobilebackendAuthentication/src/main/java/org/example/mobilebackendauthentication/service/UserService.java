package org.example.mobilebackendauthentication.service;


import lombok.Getter;
import lombok.Setter;
import org.example.mobilebackendauthentication.Entity.User;
import org.example.mobilebackendauthentication.dto.request.LoginRequest;
import org.example.mobilebackendauthentication.dto.request.RegisterRequest;
import org.example.mobilebackendauthentication.dto.request.UserResponse;
import org.example.mobilebackendauthentication.exception.DuplicateResourceException;
import org.example.mobilebackendauthentication.exception.ResourseException;
import org.example.mobilebackendauthentication.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(request.getPassword())
                .build();

        return mapToResponse(userRepository.save(user));
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::mapToResponse);
    }

    // ✅ FIXED: Changed 'Lonq' to 'Long'
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourseException("User not found with ID: " + id));
        return mapToResponse(user);
    }

    public UserResponse updateUser(Long id, RegisterRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourseException("User not found with ID: " + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobileNumber(request.getMobileNumber());

        return mapToResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourseException("User not found with ID: " + id));
        userRepository.delete(user);
    }

    public List<UserResponse> searchUsersByName(String name) {

        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourseException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResourseException("Invalid email or password");
        }

        return "Login successful";
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }
}