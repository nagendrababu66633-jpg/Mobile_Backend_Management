package org.example.usermanagementrestapi.controller;

import org.example.usermanagementrestapi.dto.UserRequest;
import org.example.usermanagementrestapi.dto.UserResponse;
import org.example.usermanagementrestapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructor Dependency Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users - Create User (Returns HTTP 201 Created)
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        UserResponse createdUser = userService.createUser(request);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    // GET /api/users - Retrieve All Users (Returns HTTP 200 OK)
    @GetMapping("user/{id}")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /api/users/{id} - Retrieve User by ID (HTTP 200 OK or 404 Not Found)
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/users/{id} - Update User (HTTP 200 OK or 404 Not Found)
    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/users/{id} - Delete User (HTTP 204 No Content or 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/users/search?query=name - Search Users (Returns HTTP 200 OK)
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam("query") String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }
}