package fr.medab.socwokbusiness.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class WebSecurityConfig {

    @Value("${fr.medab.socwork.bcrypt.strength}")
    private int bcryptSalt;

    @Value("${fr.medab.socwork.jwt.secret}")
    private String jwtSecret;

    @Value("${fr.medab.socwork.jwt.algorithm}")
    private String algorithm;

    @Value("${fr.medab.socwork.jwt.expiration}")
    private long expiration;

    @Value("${fr.medab.socwork.jwt.issuer}")
    private String issuer;

    // Authorization server config
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(bcryptSalt);
    }

    @Bean
    JwtProvider jwtProvider() {// here
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return new JwtProvider(algorithm, expiration, issuer);
    }
    // resource serverconfig

    @Bean()
    JwtDecoder jwtDecoder() { //Tell Spring how to verify JWT signatur
        SecretKey secretKey = new SecretKeySpec(jwtSecret.getBytes(), algorithm);
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.from(algorithm))
                .build();
        OAuth2TokenValidator<Jwt> jwtValidator = JwtValidators.createDefaultWithIssuer(issuer);
        decoder.setJwtValidator(jwtValidator);
        ///  default verifies expiration
        // Issuer:
        // Audience:
        // add in properties, token
        // Here: verify issuer
        // ?: why verify the issuer
        return decoder;
    }

    @Bean
        // Change default
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Defautl security filter chain Behaviour: PoLP (no authorization all)
        // Relies on JWT for authentication
        // authorize some requests or not
        // ??
        /*
         *  Autoriser les requêtes pour créer un compte et se connecter
         */
        http.cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/accounts", "/accounts/login","/accounts/sign-in").anonymous()
                                .requestMatchers(HttpMethod.GET, "/accounts/confirm").permitAll()
                                .requestMatchers(HttpMethod.GET, "/accounts/with-role").hasRole("MANAGER")
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(oauth2Config ->
                        oauth2Config
                              // .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                               .jwt(withDefaults())
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//
//        // Utiliser le claim "roles" directement comme authorities
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt ->
//            jwt.getClaimAsStringList("roles").stream()
//                    .map(SimpleGrantedAuthority::new) // Utilise les rôles avec le préfixe ROLE_ tels quels
//                    .collect(Collectors.toList()));
//
//        return jwtAuthenticationConverter;
//    }
}
