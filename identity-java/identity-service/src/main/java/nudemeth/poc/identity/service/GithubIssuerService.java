package nudemeth.poc.identity.service;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import nudemeth.poc.identity.model.issuer.IssuerUserInfo;

public class GithubIssuerService implements IssuerService {

    private String clientId;
    private String clientSecret;

    public GithubIssuerService(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public CompletableFuture<Boolean> isValidAccessToken(String accessToken) {
        return null;
    }

    @Override
    public CompletableFuture<String> getAccessToken(String code) {
        String url = getAcessTokenUrl(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        Map<String, String> uriParams = new HashMap<String, String>();
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        uriParams.put("code", code);
        uriParams.put("client_id", clientId);
        uriParams.put("client_secret", clientSecret);
        ResponseEntity<AccessTokenInfoResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                AccessTokenInfoResponse.class, uriParams);
        AccessTokenInfoResponse acessToken = response.getBody();

        return CompletableFuture.completedFuture(acessToken.getAccessToken());
    }

    @Override
    public CompletableFuture<IssuerUserInfo> getUserInfo(String accessToken) {
        return null;
    }

    private String getAcessTokenUrl(String code) {
        String urlPattern = System.getenv("GITHUB_TOKEN_URL");
        return String.format(urlPattern, code);
    }

    private class AccessTokenInfoResponse implements Serializable {
        private static final long serialVersionUID = 5747516171057971777L;

        private String accessToken;
        private String tokenType;

        public String getTokenType() {
            return tokenType;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

    }

}