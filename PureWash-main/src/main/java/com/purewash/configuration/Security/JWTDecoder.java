package com.purewash.configuration.Security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.purewash.exceptions.InvalidInput;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JWTDecoder implements JwtDecoder {

    @Value("${jwt.secretKey}")
    private String Jwt_key;
    @Value("${jwt.jwtRefreshExpiration}")
    private long Jwt_refresh_expired;
    private NimbusJwtDecoder nimbusJwtDecoder ;

    private void verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(Jwt_key);

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new InvalidInput("Refresh Token invalid");

    }

    public boolean  introspect(String token) throws JOSEException, ParseException {
        try {
            verifyToken(token);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            boolean isValid = introspect(token);

            if (!isValid) throw new JwtException("Token invalid");
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Jwt_key.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
