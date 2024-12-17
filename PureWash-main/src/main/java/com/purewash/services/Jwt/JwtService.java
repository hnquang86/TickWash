package com.purewash.services.Jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.purewash.configuration.Security.JWTDecoder;
import com.purewash.exceptions.DataNotFoundException;
import com.purewash.exceptions.InvalidInput;
import com.purewash.exceptions.TokenRefreshException;
import com.purewash.mapper.UserMapper;
import com.purewash.models.RefreshToken;
import com.purewash.models.User;
import com.purewash.repositories.RefreshTokenRepository;
import com.purewash.repositories.UserRepository;
import com.purewash.requests.UserLoginDTO;
import com.purewash.responses.Auth.TokenRefreshResponse;
import com.purewash.responses.Auth.UserLoginResponse;
import com.purewash.responses.AuthenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private final JWTDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;

    @Value("${jwt.secretKey}")
    private String Jwt_key;

    @Value("${jwt.expiration}")
    private long Jwt_expired;

    @Value("${jwt.jwtRefreshExpiration}")
    private long Jwt_refresh_expired;
    public String extractUserEmail(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaims().get("sub").toString();
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        Object rolesClaim = jwt.getClaims().get("role");

        if (rolesClaim instanceof List<?>) {
            return ((List<String>) rolesClaim).stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public AuthenResponse authenticate(UserLoginDTO request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not exists"));
        UserLoginResponse userLoginResponse = userMapper.toUserResponse(user);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new InvalidInput("Not authenticated");
        String token = generateToken(request);
        return AuthenResponse.builder().token(token).userLoginResponse(userLoginResponse).build();
    }

    private String generateToken(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("purewash.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(Jwt_expired, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("role", buildRole(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(Jwt_key.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public String generateRefreshToken(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        String tokenId =  UUID.randomUUID().toString();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("purewash.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(Jwt_refresh_expired, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(tokenId)
                .claim("type", "refresh")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(Jwt_key.getBytes()));
            String refresh_token = jwsObject.serialize();
            RefreshToken refreshToken = RefreshToken.builder()
                    .id(UUID.fromString(tokenId))
                    .expiryDate(new Date(Instant.now().plus(Jwt_refresh_expired, ChronoUnit.SECONDS).toEpochMilli()))
                    .refreshtoken(refresh_token)
                    .user(user)
                    .build();
            refreshTokenRepository.save(refreshToken);
            return refresh_token;
        } catch (JOSEException e) {
            log.error("Cannot create refresh token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildRole(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!user.getRole().name().isEmpty()) stringJoiner.add(user.getRole().name());
        return stringJoiner.toString();
    }

    public TokenRefreshResponse createAccessToken(String refresh_token) throws ParseException, JOSEException {
        if (refresh_token == null || !refresh_token.startsWith("Bearer ")) {
           throw  new TokenRefreshException(refresh_token, "Refresh token invalid");
        }

        final String token = refresh_token.substring(7);
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshtoken(token)
                .orElseThrow(() -> new TokenRefreshException(token, "Refresh token not found"));
        boolean verifiedRefreshToken = jwtDecoder.introspect(refreshToken.getRefreshtoken());
        if (!verifiedRefreshToken) throw new TokenRefreshException(token, "Refresh token invalid" );
        User user = refreshToken.getUser();
        String newAccessToken = this.generateToken(new UserLoginDTO(user.getEmail(), null));
        return TokenRefreshResponse.success(newAccessToken, token, "Get access token successfully");
    }

}