package com.purewash.services.RefreshToken;


import com.purewash.models.RefreshToken;
import com.purewash.responses.APIResponse;

import java.util.Optional;

public interface IRefreshTokenService {
     Optional<RefreshToken> findByToken(String token);
     APIResponse<Boolean> removeTokenFromUser(String refreshToken);

}
