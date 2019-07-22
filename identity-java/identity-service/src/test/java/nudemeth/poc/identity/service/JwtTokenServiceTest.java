package nudemeth.poc.identity.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier.BaseVerification;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import nudemeth.poc.identity.config.CipherConfig;
import nudemeth.poc.identity.model.UserModel;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenServiceTest {

    private CipherConfig config;

    @Before
    public void setUp() {
        this.config = new CipherConfig("1234567890123456", "key", "salt");
    }

    @Test
    public void create_WhenValidModel_ShouldReturnToken() {
        UUID uuid = UUID.randomUUID();
        String login = "TestLogin";
        UserModel model = new UserModel(uuid, login, null, null, null, null, false);
        JwtTokenService service = new JwtTokenService(config);
        String actual = service.create(model);

        Assert.assertNotNull(actual);
    }

    @Test
    public void create_WhenValidModel_ShouldBeAliveFor2Hours() {
        UUID uuid = UUID.randomUUID();
        String login = "TestLogin";
        UserModel model = new UserModel(uuid, login, null, null, null, null, false);
        JwtTokenService service = new JwtTokenService(config);
        String token = service.create(model);
        Algorithm algorithm = Algorithm.HMAC256(config.getKey());
        
        BaseVerification verification = (BaseVerification) JWT.require(algorithm);
        Clock clock = new Clock(){
        
            @Override
            public Date getToday() {
                return Date.from(Instant.now().plus(Duration.ofHours(2)).minusMillis(500));
            }
        };
        JWTVerifier verifier = verification.build(clock);

        try {
            verifier.verify(token);
        } catch (JWTVerificationException ex) {
            Assert.fail("Token lifetime is less than 2 hours.");
        }
        
    }

    private static <T> void assertThrows(Runnable throwableMethod, Class<?> expectedException, String expectedMessage) {
        try {
            throwableMethod.run();
            Assert.fail(String.format("No exception has been thrown: expected: %s with message [%s]", expectedException.getName(), expectedMessage));
        } catch (Exception ex) {
            Assert.assertEquals(expectedException.getName(), ex.getClass().getName());
            Assert.assertEquals(expectedMessage, ex.getMessage());
        }
    }
}