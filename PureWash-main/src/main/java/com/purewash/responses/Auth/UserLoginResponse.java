package com.purewash.responses.Auth;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private UUID id;
    private String email;
    private String fullName;
    private String userName;
    private String imageUrl;
    private String phoneNumber;
}
