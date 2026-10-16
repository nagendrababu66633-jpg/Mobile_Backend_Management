package org.example.mobilebackendauthentication.controller;

import org.example.mobilebackendauthentication.dto.request.LoginRequest;
import org.example.mobilebackendauthentication.dto.request.RegisterRequest;
import org.example.mobilebackendauthentication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return new  ResponseEntity(userService.createUser(request), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}