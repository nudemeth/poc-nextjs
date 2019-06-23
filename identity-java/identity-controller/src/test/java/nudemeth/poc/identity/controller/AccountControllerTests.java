package nudemeth.poc.identity.controller;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.service.UserAccountService;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTests {

    @Mock
    private UserAccountService mockAccountService;
    private AccountController accountController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountController = new AccountController(mockAccountService);
    }

    @Test
    public void getUser_WhenWithId_ShouldReturnUserModel() throws Exception {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        Optional<UserModel> user = Optional.of(new UserModel(uuid, login, issuer, token, name, email, isEmailConfirmed));
        Optional<UserModel> expected = Optional.of(new UserModel(uuid, login, issuer, token, name, email, isEmailConfirmed));

        when(mockAccountService.getUser(uuid)).thenReturn(CompletableFuture.completedFuture(user));

        CompletableFuture<Optional<UserModel>> actual = accountController.getUser(id);

        Assert.assertThat(actual.get().get(), samePropertyValuesAs(expected.get()));

        verify(mockAccountService, only()).getUser(uuid);
    }

    @Test
    public void getUser_WhenWithInvalidId_ShouldThrowResponseStatusException() throws Exception {
        String id = "some-invalid-id";
        Runnable method = () -> {
            accountController.getUser(id);
        };
        IllegalArgumentException innerEx = new IllegalArgumentException(String.format("Invalid UUID string: %s", id));
        ResponseStatusException expectedEx = new ResponseStatusException(HttpStatus.BAD_REQUEST, innerEx.getMessage(), innerEx);

        assertThrows(method, expectedEx.getClass(), expectedEx.getMessage());
        verify(mockAccountService, never()).getUser(any());
    }

    @Test
    public void getUserByLogin_WhenWithLogin_ShouldReturnUserModel() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        Optional<UserModel> user = Optional.of(new UserModel(id, login, issuer, token, name, email, isEmailConfirmed));
        Optional<UserModel> expected = Optional.of(new UserModel(id, login, issuer, token, name, email, isEmailConfirmed));

        when(mockAccountService.getUserByLogin(login)).thenReturn(CompletableFuture.completedFuture(user));
        
        CompletableFuture<Optional<UserModel>> actual = accountController.getUserByLogin(login);
        
        Assert.assertThat(actual.get().get(), samePropertyValuesAs(expected.get()));

        verify(mockAccountService, only()).getUserByLogin(login);
    }

    @Test
    public void getUserByEmail_WhenWithEmail_ShouldReturnUserModel() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        Optional<UserModel> user = Optional.of(new UserModel(id, login, issuer, token, name, email, isEmailConfirmed));
        Optional<UserModel> expected = Optional.of(new UserModel(id, login, issuer, token, name, email, isEmailConfirmed));

        when(mockAccountService.getUserByEmail(email)).thenReturn(CompletableFuture.completedFuture(user));
        
        CompletableFuture<Optional<UserModel>> actual = accountController.getUserByEmail(email);

        Assert.assertThat(actual.get().get(), samePropertyValuesAs(expected.get()));

        verify(mockAccountService, only()).getUserByEmail(email);
    }

    @Test
    public void createUser_WhenWithUserModel_ShouldReturnUUIDWith() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        UserModel user = new UserModel(login);
        user.setName(name);
        user.setEmail(email);
        
        when(mockAccountService.createUser(user)).thenReturn(CompletableFuture.completedFuture(id));

        CompletableFuture<UUID> actual = accountController.createUser(user);

        Assert.assertEquals(id, actual.get());
        
        verify(mockAccountService, only()).createUser(user);
    }

    @Test
    public void updateUser_WhenWithIdAndUserModel_ShouldReturnUserModel() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        UserModel updatingUser = new UserModel(id, login, issuer, token, name, email, isEmailConfirmed);
        UserModel updatedUser = new UserModel(id, login, issuer, token, name, email, isEmailConfirmed);
        
        when(mockAccountService.updateUser(updatingUser)).thenReturn(CompletableFuture.completedFuture(updatedUser));

        CompletableFuture<UserModel> actual = accountController.updateUser(id.toString(), updatingUser);

        Assert.assertThat(actual.get(), samePropertyValuesAs(updatedUser));
        
        verify(mockAccountService, only()).updateUser(updatingUser);
    }

    @Test
    public void updateUser_WhenIdAndModelIdNotMatched_ShouldThrowResponseStatusException() throws Exception {
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        UserModel updatingUser = new UserModel(id, login, issuer, token, name, email, isEmailConfirmed);
        Runnable method = () -> {
            accountController.updateUser(id2.toString(), updatingUser);
        };
        
        ResponseStatusException expectedEx = new ResponseStatusException(HttpStatus.BAD_REQUEST,
            String.format("Invalid updating id: %s and %s", id2.toString(), id.toString())
        );

        assertThrows(method, expectedEx.getClass(), expectedEx.getMessage());
        
        verify(mockAccountService, never()).updateUser(updatingUser);
    }

    @Test
    public void deleteUser_WhenWithId_ShouldReturnNothing() throws Exception {
        UUID id = UUID.randomUUID();
        
        accountController.deleteUser(id.toString());

        verify(mockAccountService, only()).deleteUser(id);
    }

    @Test
    public void deleteUser_WhenWithInvalidId_ShouldThrowResponseStatusException() throws Exception {
        String id = "some-invalid-uuid";
        Runnable method = () -> {
            accountController.deleteUser(id);
        };
        
        IllegalArgumentException innerEx = new IllegalArgumentException(String.format("Invalid UUID string: %s", id));
        ResponseStatusException expectedEx = new ResponseStatusException(HttpStatus.BAD_REQUEST, innerEx.getMessage(), innerEx);

        assertThrows(method, expectedEx.getClass(), expectedEx.getMessage());

        verify(mockAccountService, never()).deleteUser(any());
    }

    private static <T> void assertThrows(Runnable throwableMethod, Class<?> expectedException, String expectedMessage) {
        try {
            throwableMethod.run();
            Assert.fail(String.format("No exception has been thrown: expected: %s with message [%s]", expectedException.getName(), expectedMessage));
        } catch (Exception ex) {
            Assert.assertEquals(expectedException.getName(), ex.getClass().getName());
            Assert.assertEquals(expectedMessage, ex.getMessage());
        }
    }
}