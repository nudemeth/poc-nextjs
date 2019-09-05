package nudemeth.poc.identity.service;

import java.util.concurrent.CompletableFuture;

import nudemeth.poc.identity.model.issuer.IssuerUserInfo;

public interface IssuerService {
    CompletableFuture<Boolean> isValidAccessToken(String accessToken);
    CompletableFuture<String> getAccessToken(String code);
    CompletableFuture<IssuerUserInfo> getUserInfo(String accessToken);
}