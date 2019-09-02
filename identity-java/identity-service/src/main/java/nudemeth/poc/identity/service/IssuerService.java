package nudemeth.poc.identity.service;

import java.util.concurrent.CompletableFuture;

public interface IssuerService {
    CompletableFuture<Boolean> isValidAccessToken(String accessToken);
}