package nudemeth.poc.identity.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.mapper.UserMapper;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.model.issuer.IssuerUserInfo;
import nudemeth.poc.identity.repository.UserRepository;
import nudemeth.poc.identity.service.issuer.IssuerFactory;
import nudemeth.poc.identity.service.issuer.IssuerService;

@Service
public class UserAccountService implements AccountService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final IssuerFactory issuerFactory;

    @Autowired
    public UserAccountService(final UserRepository userRepo, final  UserMapper userMapper, final TokenService tokenService, final IssuerFactory issuerFactory) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.issuerFactory = issuerFactory;
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
                // TODO: validate issuer access token here
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
    public CompletableFuture<UUID> createOrUpdateIssuerUser(String issuer, String code) {
        UserModel newModel = getIssuerUser(issuer, code);
        UserEntity newEntity = userMapper.convertToEntity(newModel);
        UserEntity entity = userRepo.findByLoginAndIssuer(newEntity.getLogin(), newEntity.getIssuer())
            .map(e -> {
                e.setIssuerToken(newEntity.getIssuerToken());
                return e;
            })
            .orElse(newEntity);
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

    private UserModel getIssuerUser(String issuer, String code) {
        IssuerService issuerService = issuerFactory.Create(issuer);
        String accessToken = issuerService.getAccessToken(code);
        IssuerUserInfo userInfo = issuerService.getUserInfo(accessToken);
        UserModel model = new UserModel();
        
        model.setLogin(userInfo.getLogin());
        model.setIssuer(issuer);
        model.setIssuerToken(accessToken);

        return model;
    }
}