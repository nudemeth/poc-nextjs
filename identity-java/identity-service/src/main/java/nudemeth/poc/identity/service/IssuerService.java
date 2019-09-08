package nudemeth.poc.identity.service;

import nudemeth.poc.identity.model.issuer.IssuerUserInfo;

public interface IssuerService {
    Boolean isValidAccessToken(String accessToken);
    String getAccessToken(String code);
    IssuerUserInfo getUserInfo(String accessToken);
}