package nudemeth.poc.identity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestOperations;

import nudemeth.poc.identity.service.issuer.IssuerFactory;

@Configuration
public class IssuerConfigurer {

    private Environment environment;
    private RestOperations restOperations;

    public IssuerConfigurer(Environment environment, RestOperations restOperations) {
        this.environment = environment;
        this.restOperations = restOperations;
    }

    @Bean
    public IssuerFactory configIssuerFactory() {
        return new IssuerFactory(this.restOperations, this.environment);
    }

}