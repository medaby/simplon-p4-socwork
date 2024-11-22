package fr.medab.socwokbusiness.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JwtProvider {

    private Algorithm algorithm;

    public JwtProvider(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String createToken(String subject) {
        Instant issuedAt = Instant.now();
        JWTCreator.Builder builder = JWT.create()
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(issuedAt.plus(1, ChronoUnit.HOURS));
        return builder.sign(algorithm);
    }
}
