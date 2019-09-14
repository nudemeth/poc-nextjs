package nudemeth.poc.identity.service.issuer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import nudemeth.poc.identity.model.issuer.IssuerUserInfo;
import nudemeth.poc.identity.model.issuer.github.AccessTokenInfoResponse;
import nudemeth.poc.identity.model.issuer.github.GithubUserInfo;

public class GithubIssuerService implements IssuerService {

    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUrlPattern;
    private final String userInfoUrlPattern;
    private final RestOperations restOperation;
    
    @Autowired
    public GithubIssuerService(final RestOperations restOperation, final Environment environment) {
        this.restOperation = restOperation;
        this.clientId = Optional.ofNullable(System.getenv("GITHUB_CLIENT_ID")).orElse(environment.getProperty("GITHUB_CLIENT_ID"));
        this.clientSecret = Optional.ofNullable(System.getenv("GITHUB_CLIENT_SECRET")).orElse(environment.getProperty("GITHUB_CLIENT_SECRET"));
        this.accessTokenUrlPattern = Optional.ofNullable(System.getenv("GITHUB_TOKEN_URL")).orElse(environment.getProperty("GITHUB_TOKEN_URL"));
        this.userInfoUrlPattern = Optional.ofNullable(System.getenv("GITHUB_USER_INFO_URL")).orElse(environment.getProperty("GITHUB_USER_INFO_URL"));
    }

    @Override
    public Boolean isValidAccessToken(String accessToken) {
        return null;
    }

    @Override
    public String getAccessToken(String code) {
        String url = getAccessTokenUrl(code);
        HttpHeaders headers = createHttpHeaders();
        Map<String, String> uriParams = createUriParameters(code);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<AccessTokenInfoResponse> response = restOperation.exchange(url, HttpMethod.POST, entity,
                AccessTokenInfoResponse.class, uriParams);
        AccessTokenInfoResponse accessTokenInfo = response.getBody();

        return accessTokenInfo.getAccessToken();
    }

    @Override
    public IssuerUserInfo getUserInfo(String accessToken) {
        String url = userInfoUrlPattern;
        HttpHeaders headers = createUserInfoHttpHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<GithubUserInfo> response = restOperation.exchange(url, HttpMethod.GET, entity,
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

    private Map<String, String> createUriParameters(String code) {
        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("code", code);
        uriParams.put("client_id", clientId);
        uriParams.put("client_secret", clientSecret);
        return uriParams;
    }

    private HttpHeaders createUserInfoHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("Authorization", String.format("token %s", accessToken));
        headers.add("User-Agent", "poc-microservice-dev");
        return headers;
    }

    private String getAccessTokenUrl(String code) {
        return String.format(accessTokenUrlPattern, code);
    }

}