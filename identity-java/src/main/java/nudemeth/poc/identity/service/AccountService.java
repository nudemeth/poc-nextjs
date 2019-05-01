package nudemeth.poc.identity.service;

import java.util.UUID;

import nudemeth.poc.identity.model.UserModel;

public interface AccountService {
    UserModel getUser(UUID id);
    UserModel getUserByLogin(String login);
    UUID createUser(UserModel model);
    void deleteUser(UUID id);
}