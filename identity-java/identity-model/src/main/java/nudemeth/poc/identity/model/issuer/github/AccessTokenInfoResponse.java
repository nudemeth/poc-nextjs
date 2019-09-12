package nudemeth.poc.identity.model.issuer.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenInfoResponse {
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