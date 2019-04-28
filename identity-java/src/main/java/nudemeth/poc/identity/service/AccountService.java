package nudemeth.poc.identity.service;

import java.util.UUID;

import nudemeth.poc.identity.model.UserModel;

public interface AccountService {
    UserModel getUserFromLogin(String login);
    UUID createUser(UserModel model);
}