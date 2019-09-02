package nudemeth.poc.identity.service;

import java.util.concurrent.CompletableFuture;

public class GithubIssuerService implements IssuerService {

    @Override
    public CompletableFuture<Boolean> isValidAccessToken(String accessToken) {
		return null;
	}

}