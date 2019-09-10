package nudemeth.poc.identity.service;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.mapper.UserMapper;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.repository.UserRepository;
import nudemeth.poc.identity.service.issuer.IssuerFactory;
import nudemeth.poc.identity.config.CipherConfig;;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTests {

    @Mock
    private UserRepository mockUserRepo;
    @Mock
    private RestOperations mockRestOperations;
    @Mock
    private Environment mockEnvironment;
    private UserMapper userMapper;
    private CipherService cipherService;
    private UserAccountService userAccountService;
    private TokenService tokenService;
    private IssuerFactory issuerFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        CipherConfig config = new CipherConfig("1234567890123456", "key", "salt");
        cipherService = new AES256CBCCipherService(config);
        userMapper = new UserMapper(cipherService);
        tokenService = new JwtTokenService(config);
        issuerFactory = new IssuerFactory(mockRestOperations, mockEnvironment);
        userAccountService = new UserAccountService(mockUserRepo, userMapper, tokenService, issuerFactory);
    }

    @Test
    public void getUser_WhenFound_ShouldReturnUserModel() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String issuerToken = "abc";
        String encryptedToken = cipherService.encrypt(issuerToken);
        boolean isEmailConfirmed = false;
        Optional<UserEntity> entity = Optional.of(new UserEntity(id, login, issuer, encryptedToken, name, email, isEmailConfirmed));
        Optional<UserModel> expected = Optional.of(new UserModel(id, login, issuer, issuerToken, name, email, isEmailConfirmed));

        when(mockUserRepo.findById(id)).thenReturn(entity);

        CompletableFuture<Optional<UserModel>> actual = userAccountService.getUser(id);
        
        Assert.assertThat(actual.get().get(), samePropertyValuesAs(expected.get()));

        verify(mockUserRepo, only()).findById(id);
    }

    @Test
    public void getUser_WhenNotFound_ShouldReturnEmptyUserModel() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findById(id)).thenReturn(entity);

        CompletableFuture<Optional<UserModel>> actual = userAccountService.getUser(id);
        
        Assert.assertFalse(actual.get().isPresent());

        verify(mockUserRepo, only()).findById(id);
    }

    @Test
    public void getUserByLoginAndIssuer_WhenFound_ShouldReturnUserModel() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String issuerToken = "abc";
        String encryptedToken = cipherService.encrypt(issuerToken);
        boolean isEmailConfirmed = false;
        Optional<UserEntity> entity = Optional.of(new UserEntity(id, login, issuer, encryptedToken, name, email, isEmailConfirmed));
        Optional<UserModel> expected = Optional.of(new UserModel(id, login, issuer, issuerToken, name, email, isEmailConfirmed));

        when(mockUserRepo.findByLoginAndIssuer(login, issuer)).thenReturn(entity);

        CompletableFuture<Optional<UserModel>> actual = userAccountService.getUserByLoginAndIssuer(login, issuer);
        
        Assert.assertThat(actual.get().get(), samePropertyValuesAs(expected.get()));

        verify(mockUserRepo, only()).findByLoginAndIssuer(login, issuer);
    }

    @Test
    public void getUserByLoginAndIssuer_WhenNotFound_ShouldReturnEmptyUserModel()
            throws InterruptedException, ExecutionException {
        String login = "testLogin";
        String issuer = "testIssuer";
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findByLoginAndIssuer(login, issuer)).thenReturn(entity);

        CompletableFuture<Optional<UserModel>> actual = userAccountService.getUserByLoginAndIssuer(login, issuer);
        
        Assert.assertFalse(actual.get().isPresent());

        verify(mockUserRepo, only()).findByLoginAndIssuer(login, issuer);
    }

    @Test
    public void getUserByEmail_WhenFound_ShouldReturnUserModel() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String issuerToken = "abc";
        String encryptedToken = cipherService.encrypt(issuerToken);
        boolean isEmailConfirmed = false;
        Optional<UserEntity> entity = Optional.of(new UserEntity(id, login, issuer, encryptedToken, name, email, isEmailConfirmed));
        Optional<UserModel> expected = Optional.of(new UserModel(id, login, issuer, issuerToken, name, email, isEmailConfirmed));

        when(mockUserRepo.findByEmail(email)).thenReturn(entity);

        CompletableFuture<Optional<UserModel>> actual = userAccountService.getUserByEmail(email);
        
        Assert.assertThat(actual.get().get(), samePropertyValuesAs(expected.get()));

        verify(mockUserRepo, only()).findByEmail(email);
    }

    @Test
    public void getUserByEmail_WhenNotFound_ShouldReturnEmptyUserModel()
            throws InterruptedException, ExecutionException {
        String email = "Test.Email@test.com";
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findByEmail(email)).thenReturn(entity);

        CompletableFuture<Optional<UserModel>> actual = userAccountService.getUserByEmail(email);
        
        Assert.assertFalse(actual.get().isPresent());

        verify(mockUserRepo, only()).findByEmail(email);
    }

    @Test
    public void createUser_WhenSuccess_ShouldReturnUUID() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String issuerToken = "abc";
        boolean isEmailConfirmed = false;
        UserEntity entity = new UserEntity(id, login, issuer, issuerToken, name, email, isEmailConfirmed);
        UserModel model = new UserModel(login);
        model.setName(name);
        model.setEmail(email);

        when(mockUserRepo.save(any(UserEntity.class))).thenReturn(entity);

        CompletableFuture<UUID> actual = userAccountService.createUser(model);

        Assert.assertEquals(id, actual.get());

        verify(mockUserRepo, only()).save(any(UserEntity.class));
    }

    @Test
    public void deleteUser_WhenSuccess_ShouldReturnNothing() {
        UUID id = UUID.randomUUID();

        doNothing().when(mockUserRepo).deleteById(id);

        userAccountService.deleteUser(id);

        verify(mockUserRepo, only()).deleteById(id);
    }

    @Test
    public void updateUser_WhenSuccess_ShouldReturnUserModel() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String issuerToken = "abc";
        String encryptedToken = cipherService.encrypt(issuerToken);
        boolean isEmailConfirmed = false;
        UserEntity entity = new UserEntity(id, login, issuer, encryptedToken, name, email, isEmailConfirmed);
        UserModel model = new UserModel(id, login, issuer, issuerToken, name, email, isEmailConfirmed);

        when(mockUserRepo.save(any(UserEntity.class))).thenReturn(entity);

        CompletableFuture<UserModel> actual = userAccountService.updateUser(model);

        Assert.assertThat(actual.get(), samePropertyValuesAs(model));

        verify(mockUserRepo, only()).save(entity);
    }

    @Test
    public void createOrUpdateUserByLoginAndIssuer_WhenFoundInRepo_ShouldSaveAndReturnId()
            throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Github";
        String code = "Test Code";
        String issuerToken = "abc";
        String encryptedToken = cipherService.encrypt(issuerToken);
        boolean isEmailConfirmed = false;
        UserEntity entity = new UserEntity(id, login, issuer, encryptedToken, name, email, isEmailConfirmed);
        UserModel model = new UserModel(id, login, issuer, issuerToken, name, email, isEmailConfirmed);

        when(mockUserRepo.findByLoginAndIssuer(any(String.class), any(String.class))).thenReturn(Optional.of(entity));
        when(mockUserRepo.save(any(UserEntity.class))).thenReturn(entity);

        CompletableFuture<UUID> actual = userAccountService.createOrUpdateUserByLoginAndIssuer(model, code);

        Assert.assertEquals(id, actual.get());

        verify(mockUserRepo, atMost(1)).save(entity);
        verify(mockRestOperations, never()).exchange(any(String.class), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<?>>any(), ArgumentMatchers.anyMap());
    }
}