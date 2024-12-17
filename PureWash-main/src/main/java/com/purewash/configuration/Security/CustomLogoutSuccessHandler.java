package com.purewash.configuration.Security;

import com.purewash.services.RefreshToken.RefreshTokenService;
import com.purewash.utils.LocalizationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final RefreshTokenService tokenService;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String refreshToken = request.getHeader("Authorization");
        if (refreshToken != null) {
            refreshToken = refreshToken.replace("Bearer ", "");
            tokenService.removeTokenFromUser(refreshToken);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("");
    }
}

