package org.example.usermanagementapivalidation.controller;

import jakarta.validation.Valid;
import org.example.usermanagementapivalidation.model.User;
import org.example.usermanagementapivalidation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final UserService userService;

    @Autowired
    public Controller(UserService userService) {
        this.userService = userService;
    }

    // 1. CREATE USER (POST)
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // 2. GET ALL USERS (GET)
  @GetMapping("/User")
  public ResponseEntity<List<User>>getllUsers(){
        List<User>users = userService.getAllUsers();
        return new ResponseEntity<>( users,HttpStatus.OK);
  }

    // 3. UPDATE USER (PUT)@PutMapping("/user/{id}")
    @PutMapping("/user/{id}")
    public ResponseEntity<User>updateUser(@PathVariable int id,@Valid @RequestBody User user){
        User updatedUser= userService.updateUser(id,user);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);

    }

    // 4. DELETE USER (DELETE)@DeleteMapping("/user/{id}")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}