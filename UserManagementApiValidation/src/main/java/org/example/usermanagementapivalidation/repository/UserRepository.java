package org.example.usermanagementapivalidation.repository;

import org.example.usermanagementapivalidation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

}
