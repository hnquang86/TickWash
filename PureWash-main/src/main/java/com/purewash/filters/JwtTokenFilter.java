package com.purewash.filters;

import com.purewash.repositories.RefreshTokenRepository;
import com.purewash.services.Jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtTokenUtil;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            logger.info(request.getMethod() + " " +request.getRequestURI());
            if (isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT UNAUTHORIZED");
                return;
            }

            final String token = authHeader.substring(7);

//            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByRefreshtoken(token);
//            if(refreshToken.isEmpty()) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT UNAUTHORIZED");
//                return;
//            }

            final String email = jwtTokenUtil.extractUserEmail(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtTokenUtil.extractAuthorities(token);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                            jwtTokenUtil.extractUserEmail(token),
                            null,
                            authorities);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                jwtTokenUtil.extractUserEmail(token),
                                null,
                                authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    private boolean isByPassToken(@NonNull HttpServletRequest request){ //**** Public API
        final List<Pair<String,String>> byPassToken = Arrays.asList(
                Pair.of(String.format("%s/auth/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/auth/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/auth/avatar", apiPrefix), "POST"),
                Pair.of(String.format("%s/auth/refreshtoken", apiPrefix), "POST"),

                Pair.of(String.format("%s/auth/forgot-password", apiPrefix), "POST"),
                Pair.of(String.format("%s/auth/reset-password", apiPrefix), "POST"),

                Pair.of(String.format("%s/noti", apiPrefix), "POST"),

                Pair.of(String.format("%s/products/public/slug/", apiPrefix), "GET"),
                Pair.of(String.format("%s/products/public", apiPrefix), "GET"),
                Pair.of(String.format("%s/product-types", apiPrefix), "GET"),
                Pair.of(String.format("%s/products/related", apiPrefix), "GET"),

                Pair.of(String.format("%s/city", apiPrefix), "GET"),

                Pair.of(String.format("%s/reviews", apiPrefix), "GET"),
                Pair.of(String.format("%s/comments/public", apiPrefix), "GET"),

                Pair.of("/swagger-ui/**", "GET"),
                Pair.of("/swagger-ui", "GET"),
                Pair.of("/swagger-ui.html", "GET"),
                Pair.of("/swagger-ui/index.html", "GET"),
                Pair.of("/swagger/**", "GET"),
                Pair.of("/v3/api-docs/**", "GET"),
                Pair.of("/api-docs/**", "GET"),
                Pair.of("/api-docs", "GET"),
                Pair.of("/swagger-resources/**", "GET"),
                Pair.of("/swagger-resources/", "GET"),
                Pair.of("/configuration/ui", "GET"),
                Pair.of("/configuration/security", "GET")

        );
        for(Pair<String,String> bypasstoken: byPassToken) {
            if (request.getRequestURI().contains(bypasstoken.getFirst()) &&
                    request.getMethod().contains(bypasstoken.getSecond())){
                return true;
            }
        }
        return false;
    }
}
