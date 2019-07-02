package nudemeth.poc.identity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.Setter;

@Configuration
public class CipherConfigurer {

    private Environment environment;

    public CipherConfigurer(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public CipherConfig configCipher() {
        String iv = System.getenv("CIPHER_IV");
        String key = System.getenv("CIPHER_KEY");
        String salt = System.getenv("CIPHER_SALT");

        if (iv == null) {
            iv = environment.getProperty("iv");
        }

        if (key == null) {
            key = environment.getProperty("key");
        }

        if (salt == null) {
            salt = environment.getProperty("salt");
        }

        CipherConfig config = new CipherConfig(iv, key, salt);

        return config;
    }
}