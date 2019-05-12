package nudemeth.poc.identity.controller;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

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
        Optional<UserModel> user = Optional.of(new UserModel(uuid, login, name, email));
        Optional<UserModel> expected = Optional.of(new UserModel(uuid, login, name, email));

        when(mockAccountService.getUser(uuid)).thenReturn(user);

        Optional<UserModel> actual = accountController.getUser(id);

        Assert.assertThat(actual.get(), samePropertyValuesAs(expected.get()));

        verify(mockAccountService, only()).getUser(uuid);
    }

    @Test
    public void getUser_WhenWithInvalidId_ShouldThrowResponseStatusException() throws Exception {
        String id = "some-invalid-id";
        Supplier<Optional<UserModel>> method = () -> {
            return accountController.getUser(id);
        };
        IllegalArgumentException innerEx = new IllegalArgumentException(String.format("Invalid UUID string: %s", id));
        ResponseStatusException expectedEx = new ResponseStatusException(HttpStatus.BAD_REQUEST, innerEx.getMessage(), innerEx);

        assertThrows(method, expectedEx.getClass(), expectedEx.getMessage());
        verify(mockAccountService, never()).getUser(any());
    }

    private static <T> void assertThrows(Supplier<T> throwableMethod, Class<?> expectedException, String expectedMessage) {
        try {
            throwableMethod.get();
            Assert.fail(String.format("No exception has been thrown: expected: %s with message [%s]", expectedException.getName(), expectedMessage));
        } catch (Exception ex) {
            Assert.assertEquals(expectedException.getName(), ex.getClass().getName());
            Assert.assertEquals(expectedMessage, ex.getMessage());
        }
    }
}