package nudemeth.poc.identity.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.mapper.UserMapper;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.model.issuer.IssuerUserInfo;
import nudemeth.poc.identity.repository.UserRepository;

@Service
public class UserAccountService implements AccountService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final RestOperations restOperations;

    @Autowired
    public UserAccountService(final UserRepository userRepo, final  UserMapper userMapper, final  TokenService tokenService, RestOperations restOperations) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.restOperations = restOperations;
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<Optional<UserModel>> getUser(UUID id) {
        Optional<UserEntity> entity = userRepo.findById(id);
        Optional<UserModel> model = userMapper.convertToModel(entity);
        return CompletableFuture.completedFuture(model);
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<Optional<String>> getTokenByUserId(UUID id) {
        CompletableFuture<Optional<UserModel>> futureOptionalModel = getUser(id);
        return futureOptionalModel.thenApplyAsync((optionalModel) -> {
            return optionalModel.map(model -> {
                return tokenService.create(model);
            });
        });
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<Optional<UserModel>> getUserByLoginAndIssuer(String login, String issuer) {
        Optional<UserEntity> entity = userRepo.findByLoginAndIssuer(login, issuer);
        Optional<UserModel> model = userMapper.convertToModel(entity);
        return CompletableFuture.completedFuture(model);
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<Optional<UserModel>> getUserByEmail(String email) {
        Optional<UserEntity> entity = userRepo.findByEmail(email);
        Optional<UserModel> model = userMapper.convertToModel(entity);
        return CompletableFuture.completedFuture(model);
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<UUID> createUser(UserModel model) {
        UserEntity entity = userMapper.convertToEntity(model);
        UserEntity createdEntity = userRepo.save(entity);
        return CompletableFuture.completedFuture(createdEntity.getId());
    }
    
    @Override
    @Async("asyncExecutor")
    public CompletableFuture<UUID> createOrUpdateUserByLoginAndIssuer(UserModel model, String code) {
        UserEntity entity = userRepo.findByLoginAndIssuer(model.getLogin(), model.getIssuer())
            .map(e -> {
                e.setIssuerToken(model.getIssuerToken());
                e.setEmail(model.getEmail());
                if (!model.getEmail().equals(e.getEmail())) {
                    e.setEmailConfirmed(false);
                }
                return e;
            })
            .orElse(createUserByLoginAndIssuer(model, code));
        UserEntity createdEntity = userRepo.save(entity);
        return CompletableFuture.completedFuture(createdEntity.getId());
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<Void> deleteUser(UUID id) {
        userRepo.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<UserModel> updateUser(UserModel model) {
        UserEntity entity = userMapper.convertToEntity(model);
        UserEntity updatedEntity = userRepo.save(entity);
        UserModel updatedModel = userMapper.convertToModel(updatedEntity);
        return CompletableFuture.completedFuture(updatedModel);
    }

    private UserEntity createUserByLoginAndIssuer(UserModel model, String code) {
        IssuerService issuerService = getIssuerService(model.getIssuer());
        String accessToken = issuerService.getAccessToken(code);
        IssuerUserInfo userInfo = issuerService.getUserInfo(accessToken);

        model.setLogin(userInfo.getLogin());
        model.setIssuerToken(accessToken);

        return userMapper.convertToEntity(model);
    }

    private IssuerService getIssuerService(final String issuer) {
        if (issuer == null || issuer.isEmpty()) {
            throw new IllegalArgumentException("Issuer cannot be null or empty");
        }

        String formattedIssuer = issuer.toLowerCase();
        switch (formattedIssuer) {
            case "github":
                return new GithubIssuerService(restOperations);
            default:
                throw new IllegalArgumentException(String.format("Issuer is invalid: %s", issuer));
        }
    }
}