package com.purewash.responses.User;

import com.purewash.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {

    private UUID id;
    private String name;
    private String password;
    private String email;
    private Role role;
    private String phoneNumber;

}
