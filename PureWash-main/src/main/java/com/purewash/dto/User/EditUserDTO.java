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
public class EditUserDTO {

    public UUID id;
    public String email;
    private String role;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;

}
