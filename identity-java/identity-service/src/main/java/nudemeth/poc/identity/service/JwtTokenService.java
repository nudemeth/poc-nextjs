package nudemeth.poc.identity.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.stereotype.Service;

import nudemeth.poc.identity.model.UserModel;

@Service
public class JwtTokenService implements TokenService {

    private final int TOKEN_LIFETIME_HOURS = 2;

    @Override
    public String create(UserModel model) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String exp = ZonedDateTime.now().plusHours(TOKEN_LIFETIME_HOURS).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String token = JWT.create()
            .withClaim("id", model.getId().toString())
            .withClaim("login", model.getLogin())
            .withClaim("exp", exp)
            .sign(algorithm);
        return token;
    }

    @Override
    public boolean verify(String token) {
        return false;
    }

}