package fr.medab.socwokbusiness.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

public class JwtProvider {

    private final Algorithm algorithm;
    private final long expiration;
    private final String issuer;

    public JwtProvider(Algorithm algorithm, long expiration, String issuer) {
        this.algorithm = algorithm;
        this.expiration = expiration;
        this.issuer = issuer;
    }

    public String createToken(String subject, List<String> roles) {
        Instant issuedAt = Instant.now();
        JWTCreator.Builder builder = JWT.create()
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withClaim("roles", roles)
                .withIssuer(issuer);

        if (expiration > -1) {
            Instant expiresAt = issuedAt.plus(expiration, ChronoUnit.SECONDS);
            builder.withExpiresAt(expiresAt);
        }

        return builder.sign(algorithm);

//        JWTCreator.Builder builder = JWT.create()
//                .withSubject(subject)
//                .withIssuedAt(issuedAt)
//                .withExpiresAt(issuedAt.plus(1, ChronoUnit.HOURS));
//        return builder.sign(algorithm);
    }
}
