package com.purewash.services.RefreshToken;

import com.purewash.exceptions.DataNotFoundException;
import com.purewash.exceptions.TokenRefreshException;
import com.purewash.models.RefreshToken;
import com.purewash.models.User;
import com.purewash.repositories.AuthRepository;
import com.purewash.repositories.RefreshTokenRepository;
import com.purewash.responses.APIResponse;
import com.purewash.utils.LocalizationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {
    @Value("${jwt.jwtRefreshExpiration}")
    private Long jwtRefreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthRepository authRepository;
    private final LocalizationUtils localizationUtils;
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshtoken(token);
    }

    @Override
    public APIResponse<Boolean> removeTokenFromUser(String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.replace("Bearer ", "");
        }
        RefreshToken refresh_token = refreshTokenRepository.findByRefreshtoken(refreshToken)
                .orElseThrow(() -> new DataNotFoundException(""));
        refreshTokenRepository.delete(refresh_token);
        return new APIResponse<>(true, "");
    }

}
