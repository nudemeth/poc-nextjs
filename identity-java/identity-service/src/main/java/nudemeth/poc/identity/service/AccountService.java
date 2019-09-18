package nudemeth.poc.identity.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import nudemeth.poc.identity.model.UserModel;

public interface AccountService {
    CompletableFuture<Optional<UserModel>> getUser(UUID id);
    CompletableFuture<Optional<String>> getTokenByUserId(UUID id);
    CompletableFuture<Optional<UserModel>> getUserByLoginAndIssuer(String login, String issuer);
    CompletableFuture<Optional<UserModel>> getUserByEmail(String email);
    CompletableFuture<UUID> createUser(UserModel model);
    CompletableFuture<UUID> createOrUpdateIssuerUser(String issuer, String code);
    CompletableFuture<Void> deleteUser(UUID id);
    CompletableFuture<UserModel> updateUser(UserModel model);
}