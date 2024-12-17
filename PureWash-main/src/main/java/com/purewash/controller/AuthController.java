package com.purewash.controller;

import com.purewash.constants.Endpoint;
import com.purewash.models.User;
import com.purewash.requests.Auth.UserRegisterDTO;
import com.purewash.requests.UserLoginDTO;
import com.purewash.responses.Auth.LoginResponse;
import com.purewash.responses.Auth.RegisterResponse;
import com.purewash.responses.Auth.TokenRefreshResponse;
import com.purewash.responses.Auth.UserLoginResponse;
import com.purewash.responses.AuthenResponse;
import com.purewash.services.Auth.AuthService;
import com.purewash.services.Jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.Auth.BASE)
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping(Endpoint.Auth.REGISTER)
    public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User registerUser = authService.createUser(userRegisterDTO);
            return ResponseEntity.ok(new RegisterResponse("Register Successfully", registerUser));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponse(ex.getMessage(), null));
        }
    }

    @PostMapping(Endpoint.Auth.LOGIN)
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            AuthenResponse authenResponse = jwtService.authenticate(userLoginDTO);
            String access_token = authenResponse.getToken();
            String refresh_token = jwtService.generateRefreshToken(userLoginDTO);
            UserLoginResponse userLoginResponse = authenResponse.getUserLoginResponse();
            return ResponseEntity.ok(LoginResponse.success("Login successfully",
                    access_token, refresh_token, userLoginResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(LoginResponse.failure(e.getMessage()));
        }
    }

    @PostMapping(Endpoint.Auth.REFRESH_TOKEN)
    public ResponseEntity<TokenRefreshResponse> refreshtoken(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok().body(jwtService.createAccessToken(token));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(TokenRefreshResponse.failure(ex.getMessage()));
        }
    }
    
}
