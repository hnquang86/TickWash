package com.purewash.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDTO {
    private String id;
    private String role;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
}
