package nudemeth.poc.identity.service;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.mapper.UserMapper;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTests {

    @Mock
    private UserRepository mockUserRepo;
    private UserMapper userMapper;
    private UserAccountService userAccountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userMapper = new UserMapper();
        userAccountService = new UserAccountService(mockUserRepo, userMapper);
    }

    @Test
    public void getUser_WhenFound_ShouldReturnUserModel() {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        Optional<UserEntity> entity = Optional.of(new UserEntity(id, login, issuer, token, name, email));
        Optional<UserModel> expected = Optional.of(new UserModel(id, login, issuer, token, name, email));

        when(mockUserRepo.findById(id)).thenReturn(entity);

        Optional<UserModel> actual = userAccountService.getUser(id);
        
        Assert.assertThat(actual.get(), samePropertyValuesAs(expected.get()));

        verify(mockUserRepo, only()).findById(id);
    }

    @Test
    public void getUser_WhenNotFound_ShouldReturnEmptyUserModel() {
        UUID id = UUID.randomUUID();
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findById(id)).thenReturn(entity);

        Optional<UserModel> actual = userAccountService.getUser(id);
        
        Assert.assertFalse(actual.isPresent());

        verify(mockUserRepo, only()).findById(id);
    }

    @Test
    public void getUserByLogin_WhenFound_ShouldReturnUserModel() {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        Optional<UserEntity> entity = Optional.of(new UserEntity(id, login, issuer, token, name, email));
        Optional<UserModel> expected = Optional.of(new UserModel(id, login, issuer, token, name, email));

        when(mockUserRepo.findByLogin(login)).thenReturn(entity);

        Optional<UserModel> actual = userAccountService.getUserByLogin(login);
        
        Assert.assertThat(actual.get(), samePropertyValuesAs(expected.get()));

        verify(mockUserRepo, only()).findByLogin(login);
    }

    @Test
    public void getUserByLogin_WhenNotFound_ShouldReturnEmptyUserModel() {
        String login = "testLogin";
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findByLogin(login)).thenReturn(entity);

        Optional<UserModel> actual = userAccountService.getUserByLogin(login);
        
        Assert.assertFalse(actual.isPresent());

        verify(mockUserRepo, only()).findByLogin(login);
    }

    @Test
    public void getUserByEmail_WhenFound_ShouldReturnUserModel() {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        Optional<UserEntity> entity = Optional.of(new UserEntity(id, login, issuer, token, name, email));
        Optional<UserModel> expected = Optional.of(new UserModel(id, login, issuer, token, name, email));

        when(mockUserRepo.findByEmail(email)).thenReturn(entity);

        Optional<UserModel> actual = userAccountService.getUserByEmail(email);
        
        Assert.assertThat(actual.get(), samePropertyValuesAs(expected.get()));

        verify(mockUserRepo, only()).findByEmail(email);
    }

    @Test
    public void getUserByEmail_WhenNotFound_ShouldReturnEmptyUserModel() {
        String email = "Test.Email@test.com";
        Optional<UserEntity> entity = Optional.empty();
        
        when(mockUserRepo.findByEmail(email)).thenReturn(entity);

        Optional<UserModel> actual = userAccountService.getUserByEmail(email);
        
        Assert.assertFalse(actual.isPresent());

        verify(mockUserRepo, only()).findByEmail(email);
    }

    @Test
    public void createUser_WhenSuccess_ShouldReturnUUID() {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        UserEntity entity = new UserEntity(id, login, issuer, token, name, email);
        UserModel model = new UserModel(login);
        model.setName(name);
        model.setEmail(email);

        when(mockUserRepo.save(any(UserEntity.class))).thenReturn(entity);

        UUID actual = userAccountService.createUser(model);

        Assert.assertEquals(id, actual);

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
    public void updateUser_WhenSuccess_ShouldReturnUserModel() {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        UserEntity entity = new UserEntity(id, login, issuer, token, name, email);
        UserModel model = new UserModel(id, login, issuer, token, name, email);

        when(mockUserRepo.save(any(UserEntity.class))).thenReturn(entity);

        UserModel actual = userAccountService.updateUser(model);

        Assert.assertThat(actual, samePropertyValuesAs(model));

        verify(mockUserRepo, only()).save(entity);
    }
}