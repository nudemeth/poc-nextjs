package nudemeth.poc.identity.service;

import java.util.Optional;
import java.util.UUID;

import nudemeth.poc.identity.model.UserModel;

public interface AccountService {
    Optional<UserModel> getUser(UUID id);
    Optional<UserModel> getUserByLogin(String login);
    UUID createUser(UserModel model);
    void deleteUser(UUID id);
    UserModel updateUser(UserModel model);
}