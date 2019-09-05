package nudemeth.poc.identity.service;

import java.util.concurrent.CompletableFuture;

import nudemeth.poc.identity.model.issuer.IssuerUserInfo;

public class GithubIssuerService implements IssuerService {

    public GithubIssuerService(String clientId, String secret) {

    }

    @Override
    public CompletableFuture<Boolean> isValidAccessToken(String accessToken) {
        return null;
    }

    @Override
    public CompletableFuture<String> getAccessToken(String code) {
        return null;
    }

    @Override
    public CompletableFuture<IssuerUserInfo> getUserInfo(String accessToken) {
        return null;
    }

}