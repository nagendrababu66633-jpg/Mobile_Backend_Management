package org.example.mobilebackendauthentication.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private long id;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobileNumber;
    private LocalDateTime createdAt;


}
