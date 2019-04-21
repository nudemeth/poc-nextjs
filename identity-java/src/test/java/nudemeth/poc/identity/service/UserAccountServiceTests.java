package nudemeth.poc.identity.service;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.mapper.UserMapper;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.repository.UserRepository;

@RunWith(SpringRunner.class)
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
    public void getUserFromLogin_WhenFound_ShouldReturnUserModel() {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        UserEntity entity = new UserEntity(id, login, name, email);
        UserModel expected = new UserModel(id, login, name, email);

        when(mockUserRepo.findByLogin(login)).thenReturn(entity);

        UserModel actual = userAccountService.getUserFromLogin(login);
        
        Assert.assertThat(actual, samePropertyValuesAs(expected));
    }
}