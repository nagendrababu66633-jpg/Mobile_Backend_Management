package org.example.usermanagementrestapi.repository;

import org.example.usermanagementrestapi.model.User;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private final Map<Long, User> userStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        userStorage.put(user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(userStorage.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userStorage.get(id));
    }

    public boolean deleteById(Long id) {
        return userStorage.remove(id) != null;
    }

    public List<User> searchByName(String nameQuery) {
        return userStorage.values().stream()
                .filter(user -> user.getName() != null &&
                        user.getName().toLowerCase().contains(nameQuery.toLowerCase()))
                .collect(Collectors.toList());
    }
}