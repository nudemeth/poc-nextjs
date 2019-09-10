package nudemeth.poc.identity.service.issuer;

import org.springframework.core.env.Environment;
import org.springframework.web.client.RestOperations;

public class IssuerFactory {

    private final RestOperations restOperations;
    private final Environment environment;

    public IssuerFactory(final RestOperations restOperations, final Environment environment) {
        this.restOperations = restOperations;
        this.environment = environment;
    }

    public IssuerService Create(final String issuer) {
        if (issuer == null || issuer.isEmpty()) {
            throw new IllegalArgumentException("Issuer cannot be null or empty");
        }

        String formattedIssuer = issuer.toLowerCase();
        switch (formattedIssuer) {
            case "github":
                return new GithubIssuerService(restOperations, environment);
            default:
                throw new IllegalArgumentException(String.format("Issuer is invalid: %s", issuer));
        }
    }

}