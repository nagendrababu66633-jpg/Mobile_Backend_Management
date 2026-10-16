package org.example.usermanagementapivalidation.service;

import org.example.usermanagementapivalidation.model.User;
import org.example.usermanagementapivalidation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. CREATE USER (Fixed spelling to 'createUser')
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // 2. GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 3. GET USER BY ID
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    // 4. UPDATE USER
    public User updateUser(int id, User user) {
        return userRepository.save(user);
    }

    // 5. DELETE USER
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}