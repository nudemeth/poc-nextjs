package nudemeth.poc.identity.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nudemeth.poc.identity.config.CipherConfig;
import nudemeth.poc.identity.model.UserModel;

@Service
public class JwtTokenService implements TokenService {

    private static final int TOKEN_LIFETIME_HOURS = 2;

    private Algorithm algorithm;

    @Autowired
    public JwtTokenService(CipherConfig config) {
        this.algorithm = Algorithm.HMAC256(config.getKey());
    }

    @Override
    public String create(UserModel model) {
        if (model == null) {
            throw new IllegalArgumentException("model argument cannot be null.");
        }
        String token = JWT.create()
            .withClaim("id", model.getId().toString())
            .withClaim("login", model.getLogin())
            .withExpiresAt(Date.from(Instant.now().plus(Duration.ofHours(TOKEN_LIFETIME_HOURS))))
            .sign(algorithm);
        return token;
    }

    @Override
    public boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }
}