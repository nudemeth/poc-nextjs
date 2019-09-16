package nudemeth.poc.identity.service.issuer;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import nudemeth.poc.identity.model.issuer.IssuerUserInfo;
import nudemeth.poc.identity.model.issuer.github.AccessTokenInfoResponse;
import nudemeth.poc.identity.model.issuer.github.GithubUserInfo;
import nudemeth.poc.identity.model.issuer.github.ValidationResponse;

public class GithubIssuerService implements IssuerService {

    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUrlPattern;
    private final String userInfoUrlPattern;
    private final String validationUrlPattern;
    private final RestOperations restOperation;
    
    @Autowired
    public GithubIssuerService(final RestOperations restOperation, final Environment environment) {
        this.restOperation = restOperation;
        this.clientId = Optional.ofNullable(System.getenv("GITHUB_CLIENT_ID")).orElse(environment.getProperty("GITHUB_CLIENT_ID"));
        this.clientSecret = Optional.ofNullable(System.getenv("GITHUB_CLIENT_SECRET")).orElse(environment.getProperty("GITHUB_CLIENT_SECRET"));
        this.accessTokenUrlPattern = Optional.ofNullable(System.getenv("GITHUB_TOKEN_URL")).orElse(environment.getProperty("GITHUB_TOKEN_URL"));
        this.userInfoUrlPattern = Optional.ofNullable(System.getenv("GITHUB_USER_INFO_URL")).orElse(environment.getProperty("GITHUB_USER_INFO_URL"));
        this.validationUrlPattern = Optional.ofNullable(System.getenv("GITHUB_VALIDATION_URL")).orElse(environment.getProperty("GITHUB_VALIDATION_URL"));
    }

    @Override
    public Boolean isAccessTokenValid(String accessToken) {
        HttpHeaders headers = createAccessTokenValidationHttpHeaders();
        Map<String, String> uriParams = createValidationUriParams(accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<ValidationResponse> response = restOperation.exchange(validationUrlPattern, HttpMethod.GET, entity,
            ValidationResponse.class, uriParams);
        HttpStatus statusCode = response.getStatusCode();

        return statusCode.is2xxSuccessful();
    }

    @Override
    public String getAccessToken(String code) {
        HttpHeaders headers = createHttpHeaders();
        Map<String, String> uriParams = createAccessTokenUriParams(code);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<AccessTokenInfoResponse> response = restOperation.exchange(accessTokenUrlPattern, HttpMethod.POST, entity,
                AccessTokenInfoResponse.class, uriParams);
        AccessTokenInfoResponse accessTokenInfo = response.getBody();

        return accessTokenInfo.getAccessToken();
    }

    @Override
    public IssuerUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = createUserInfoHttpHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<GithubUserInfo> response = restOperation.exchange(userInfoUrlPattern, HttpMethod.GET, entity,
            GithubUserInfo.class);
        System.out.println(response == null);
        GithubUserInfo userInfo = response.getBody();
        return userInfo;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        return headers;
    }

    private Map<String, String> createAccessTokenUriParams(String code) {
        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("code", code);
        uriParams.put("client_id", clientId);
        uriParams.put("client_secret", clientSecret);
        return uriParams;
    }

    private Map<String, String> createValidationUriParams(String accessToken) {
        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("client_id", clientId);
        uriParams.put("access_token", accessToken);
        return uriParams;
    }

    private HttpHeaders createUserInfoHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("User-Agent", "poc-microservice-dev");
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private HttpHeaders createAccessTokenValidationHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.setBasicAuth(this.clientId, this.clientSecret, Charset.forName("UTF-8"));
        return headers;
    }

}