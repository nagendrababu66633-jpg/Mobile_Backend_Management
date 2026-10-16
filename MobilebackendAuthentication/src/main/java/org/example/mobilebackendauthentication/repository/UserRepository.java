package org.example.mobilebackendauthentication.repository;

import org.example.mobilebackendauthentication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<User> findByEmail(String email);

    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase
            ( String firstName,
            String lastName
    );
}
