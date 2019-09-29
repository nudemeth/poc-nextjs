package nudemeth.poc.identity.service;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.exception.InvalidTokenException;
import nudemeth.poc.identity.mapper.UserMapper;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.model.issuer.github.AccessTokenInfoResponse;
import nudemeth.poc.identity.model.issuer.github.GithubUserInfo;
import nudemeth.poc.identity.model.issuer.github.ValidationResponse;
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

        verify(mockUserRepo, times(1)).findById(id);
    }

    @Test
    public void getUser_WhenNotFound_ShouldReturnEmptyUserModel() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findById(id)).thenReturn(entity);

        CompletableFuture<Optional<UserModel>> actual = userAccountService.getUser(id);
        
        Assert.assertFalse(actual.get().isPresent());

        verify(mockUserRepo, times(1)).findById(id);
    }

    @Test
    public void getTokenByUserId_When404_ShouldThrowInvalidTokenException() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "github";
        String issuerToken = "abc";
        String encryptedToken = cipherService.encrypt(issuerToken);
        boolean isEmailConfirmed = false;
        String validationUrl = "http://mock.validation";
        String clientId = "testId";
        String clientSecret = "testSecret";
        Optional<UserEntity> entity = Optional.of(new UserEntity(id, login, issuer, encryptedToken, name, email, isEmailConfirmed));
        ValidationResponse validationBody = new ValidationResponse();
        ResponseEntity<ValidationResponse> validationResponse = new ResponseEntity<ValidationResponse>(validationBody, HttpStatus.NOT_FOUND);
        
        when(mockUserRepo.findById(id)).thenReturn(entity);
        when(mockEnvironment.getProperty("issuer.github.validation.url")).thenReturn(validationUrl);
        when(mockEnvironment.getProperty("issuer.github.client.id")).thenReturn(clientId);
        when(mockEnvironment.getProperty("issuer.github.client.secret")).thenReturn(clientSecret);
        when(mockRestOperations.exchange(eq(validationUrl), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<ValidationResponse>>any(), ArgumentMatchers.anyMap())).thenReturn(validationResponse);

        CompletableFuture<Optional<String>> actual = userAccountService.getTokenByUserId(id);
        InvalidTokenException innerEx = new InvalidTokenException("Invalid access token for issuer: github");
        CompletionException expectedEx = new CompletionException(innerEx);

        actual.exceptionally(ex -> {
            Assert.assertEquals(expectedEx.getClass(), ex.getClass());
            Assert.assertEquals(expectedEx.getMessage(), ex.getMessage());
            return Optional.empty();
        }).join();
        
        verify(mockUserRepo, times(1)).findById(id);
    }

    @Test
    public void getTokenByUserId_When200_ShouldReturnToken() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "github";
        String issuerToken = "abc";
        String encryptedToken = cipherService.encrypt(issuerToken);
        boolean isEmailConfirmed = false;
        String validationUrl = "http://mock.validation";
        String clientId = "testId";
        String clientSecret = "testSecret";
        Optional<UserEntity> entity = Optional.of(new UserEntity(id, login, issuer, encryptedToken, name, email, isEmailConfirmed));
        ValidationResponse validationBody = new ValidationResponse();
        ResponseEntity<ValidationResponse> validationResponse = new ResponseEntity<ValidationResponse>(validationBody, HttpStatus.OK);
        
        when(mockUserRepo.findById(id)).thenReturn(entity);
        when(mockEnvironment.getProperty("issuer.github.validation.url")).thenReturn(validationUrl);
        when(mockEnvironment.getProperty("issuer.github.client.id")).thenReturn(clientId);
        when(mockEnvironment.getProperty("issuer.github.client.secret")).thenReturn(clientSecret);
        when(mockRestOperations.exchange(eq(validationUrl), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<ValidationResponse>>any(), ArgumentMatchers.anyMap())).thenReturn(validationResponse);

        CompletableFuture<Optional<String>> actual = userAccountService.getTokenByUserId(id);
        
        Assert.assertTrue(tokenService.verify(actual.get().get()));
        
        verify(mockUserRepo, times(1)).findById(id);
    }

    @Test
    public void getTokenByUserId_WhenFound_ShouldReturnToken() throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findById(id)).thenReturn(entity);

        CompletableFuture<Optional<String>> actual = userAccountService.getTokenByUserId(id);
        
        Assert.assertFalse(actual.get().isPresent());

        verify(mockUserRepo, times(1)).findById(id);
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

        verify(mockUserRepo, times(1)).findByLoginAndIssuer(login, issuer);
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

        verify(mockUserRepo, times(1)).findByLoginAndIssuer(login, issuer);
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

        verify(mockUserRepo, times(1)).findByEmail(email);
    }

    @Test
    public void getUserByEmail_WhenNotFound_ShouldReturnEmptyUserModel()
            throws InterruptedException, ExecutionException {
        String email = "Test.Email@test.com";
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findByEmail(email)).thenReturn(entity);

        CompletableFuture<Optional<UserModel>> actual = userAccountService.getUserByEmail(email);
        
        Assert.assertFalse(actual.get().isPresent());

        verify(mockUserRepo, times(1)).findByEmail(email);
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

        verify(mockUserRepo, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void deleteUser_WhenSuccess_ShouldReturnNothing() {
        UUID id = UUID.randomUUID();

        doNothing().when(mockUserRepo).deleteById(id);

        userAccountService.deleteUser(id);

        verify(mockUserRepo, times(1)).deleteById(id);
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

        verify(mockUserRepo, times(1)).save(entity);
    }

    @Test
    public void createOrUpdateUserByLoginAndIssuer_WhenFoundInRepo_ShouldSaveUpdatedTokenAndReturnId()
            throws InterruptedException, ExecutionException {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Github";
        String code = "Test Code";
        String issuerToken = "abc";
        String newIssuerToken = "def";
        String encryptedToken = cipherService.encrypt(issuerToken);
        String newEncryptedToken = cipherService.encrypt(newIssuerToken);
        boolean isEmailConfirmed = false;
        UserEntity entity = new UserEntity(id, login, issuer, encryptedToken, name, email, isEmailConfirmed);
        UserEntity newEntity = new UserEntity(id, login, issuer, newEncryptedToken, name, email, isEmailConfirmed);
        String tokenUrl = "http://mock.token";
        String userInfoUrl = "http://mock.userinfo";
        AccessTokenInfoResponse accessTokenBody = new AccessTokenInfoResponse();
        ResponseEntity<AccessTokenInfoResponse> accessTokenResponse = new ResponseEntity<AccessTokenInfoResponse>(accessTokenBody, HttpStatus.OK);
        GithubUserInfo userInfoBody = new GithubUserInfo();
        ResponseEntity<GithubUserInfo> userInfoResponse = new ResponseEntity<GithubUserInfo>(userInfoBody, HttpStatus.OK);

        accessTokenBody.setAccessToken(newIssuerToken);
        userInfoBody.setLogin(login);

        when(mockRestOperations.exchange(eq(tokenUrl), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<AccessTokenInfoResponse>>any(), ArgumentMatchers.anyMap())).thenReturn(accessTokenResponse);
        when(mockRestOperations.exchange(eq(userInfoUrl), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<GithubUserInfo>>any())).thenReturn(userInfoResponse);
        when(mockEnvironment.getProperty("issuer.github.token.url")).thenReturn(tokenUrl);
        when(mockEnvironment.getProperty("issuer.github.userinfo.url")).thenReturn(userInfoUrl);
        when(mockUserRepo.findByLoginAndIssuer(any(String.class), any(String.class))).thenReturn(Optional.of(entity));
        when(mockUserRepo.save(any(UserEntity.class))).thenReturn(entity);

        CompletableFuture<UUID> actual = userAccountService.createOrUpdateIssuerUser(issuer, code);

        ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);

        verify(mockUserRepo, times(1)).save(argument.capture());
        verify(mockRestOperations, times(1)).exchange(any(String.class), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<AccessTokenInfoResponse>>any(), ArgumentMatchers.anyMap());
        verify(mockRestOperations, times(1)).exchange(any(String.class), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<GithubUserInfo>>any(), ArgumentMatchers.anyMap());

        Assert.assertEquals(newEntity, argument.getValue());
        Assert.assertEquals(id, actual.get());
    }

    @Test
    public void createOrUpdateUserByLoginAndIssuer_WhenNotFoundInRepo_ShouldCallIssuerAndSaveAndReturnId()
            throws InterruptedException, ExecutionException {
        UUID id = null;
        String login = "testLogin";
        String issuer = "Github";
        String code = "Test Code";
        String issuerToken = "abc";
        String encryptedToken = cipherService.encrypt(issuerToken);
        UserEntity entity = new UserEntity(null, login, issuer, encryptedToken, null, null, false);
        String tokenUrl = "http://mock.token";
        String userInfoUrl = "http://mock.userinfo";
        AccessTokenInfoResponse accessTokenBody = new AccessTokenInfoResponse();
        ResponseEntity<AccessTokenInfoResponse> accessTokenResponse = new ResponseEntity<AccessTokenInfoResponse>(accessTokenBody, HttpStatus.OK);
        GithubUserInfo userInfoBody = new GithubUserInfo();
        ResponseEntity<GithubUserInfo> userInfoResponse = new ResponseEntity<GithubUserInfo>(userInfoBody, HttpStatus.OK);

        accessTokenBody.setAccessToken(issuerToken);
        userInfoBody.setLogin(login);

        when(mockRestOperations.exchange(eq(tokenUrl), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<AccessTokenInfoResponse>>any(), ArgumentMatchers.anyMap())).thenReturn(accessTokenResponse);
        when(mockRestOperations.exchange(eq(userInfoUrl), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<GithubUserInfo>>any())).thenReturn(userInfoResponse);
        when(mockEnvironment.getProperty("issuer.github.token.url")).thenReturn(tokenUrl);
        when(mockEnvironment.getProperty("issuer.github.userinfo.url")).thenReturn(userInfoUrl);
        when(mockUserRepo.findByLoginAndIssuer(any(String.class), any(String.class))).thenReturn(Optional.empty());
        when(mockUserRepo.save(any(UserEntity.class))).thenReturn(entity);

        CompletableFuture<UUID> actual = userAccountService.createOrUpdateIssuerUser(issuer, code);

        ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);

        verify(mockUserRepo, times(1)).save(argument.capture());
        verify(mockRestOperations, times(1)).exchange(any(String.class), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<AccessTokenInfoResponse>>any(), ArgumentMatchers.anyMap());
        verify(mockRestOperations, times(1)).exchange(any(String.class), any(HttpMethod.class), ArgumentMatchers.<HttpEntity<String>>any(), ArgumentMatchers.<Class<GithubUserInfo>>any(), ArgumentMatchers.anyMap());

        Assert.assertEquals(entity, argument.getValue());
        Assert.assertEquals(id, actual.get());
    }
}