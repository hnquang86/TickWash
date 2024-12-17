package com.purewash.services.Auth;

import com.purewash.mapper.UserMapper;
import com.purewash.models.Enums.RoleEnum;
import com.purewash.models.User;
import com.purewash.repositories.AuthRepository;
import com.purewash.repositories.RefreshTokenRepository;
import com.purewash.repositories.UserRepository;
import com.purewash.requests.Auth.UserRegisterDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public User createUser(UserRegisterDTO userRegisterDTO) {
        String email = userRegisterDTO.getMail();
        Optional<User> optionalUser = authRepository.findByEmail(email);
        if (optionalUser.isPresent()) throw new BadCredentialsException("User already existed");
        User newUser = User.builder()
                .email(userRegisterDTO.getMail())
                .password(userRegisterDTO.getPassword())
                .status(1)
                .fullName(userRegisterDTO.getMail())
                .userName(userRegisterDTO.getMail())
                .role(RoleEnum.BASIC)
                .build();
        authRepository.save(newUser);
        String password = userRegisterDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        return authRepository.save(newUser);
    }


}
