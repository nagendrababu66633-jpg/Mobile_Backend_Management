package org.example.mobilebackendauthentication.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="firstname",nullable = false)
    private String firstName;// first_name
    @Column(name="lastname",nullable=false)
    private String lastName;
    @Column(nullable = false, unique=true)
    private String email;
    @Column (name="mobilenumber",nullable = false)
    private String mobileNumber;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
   @Column(updatable = false )
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public User(){
    }
}
