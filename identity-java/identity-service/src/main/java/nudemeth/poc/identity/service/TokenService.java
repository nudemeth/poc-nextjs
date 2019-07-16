package nudemeth.poc.identity.service;

import nudemeth.poc.identity.model.UserModel;

public interface TokenService {
    String create(UserModel model);
    boolean verify(String token);
}