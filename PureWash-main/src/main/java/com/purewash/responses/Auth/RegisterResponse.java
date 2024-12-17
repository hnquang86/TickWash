package com.purewash.responses.Auth;

import com.purewash.models.User;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterResponse {
    private String message;
    private User user;
}
