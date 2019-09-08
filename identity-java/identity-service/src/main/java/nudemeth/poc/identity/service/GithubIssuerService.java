package nudemeth.poc.identity.service;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import nudemeth.poc.identity.model.issuer.IssuerUserInfo;

public class GithubIssuerService implements IssuerService {

    private final String clientId;
    private final String clientSecret;
    private final RestOperations restTemplate;

    @Autowired
    public GithubIssuerService(final RestOperations restTemplate) {
        this.clientId = System.getenv("GITHUB_CLIENT_ID");
        this.clientSecret = System.getenv("GITHUB_CLIENT_SECRET");
        this.restTemplate = restTemplate;
    }

    @Override
    public Boolean isValidAccessToken(String accessToken) {
        return null;
    }

    @Override
    public String getAccessToken(String code) {
        String url = getAcessTokenUrl(code);
        HttpHeaders headers = createHttpHeaders();
        Map<String, String> uriParams = createUriParameters(code);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<AccessTokenInfoResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                AccessTokenInfoResponse.class, uriParams);
        AccessTokenInfoResponse accessTokenInfo = response.getBody();

        return accessTokenInfo.getAccessToken();
    }

    @Override
    public IssuerUserInfo getUserInfo(String accessToken) {
        return null;
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

    private String getAcessTokenUrl(String code) {
        String urlPattern = System.getenv("GITHUB_TOKEN_URL");
        return String.format(urlPattern, code);
    }

    private class AccessTokenInfoResponse {
        private String accessToken;
        private String tokenType;

        @JsonProperty("token_type")
        public String getTokenType() {
            return this.tokenType;
        }

        @JsonProperty("token_type")
        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        @JsonProperty("access_token")
        public String getAccessToken() {
            return this.accessToken;
        }

        @JsonProperty("access_token")
        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

    }

}