package nudemeth.poc.identity.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService mockAccountService;

    @Test
    public void getUser_WhenNoLoginPathParam_ShouldReturnMethodNotAllowed() throws Exception {
        this.mockMvc.perform(get("/users"))
            .andDo(print())
            .andExpect(status().isMethodNotAllowed())
            .andExpect(status().reason("Request method 'GET' not supported"));

        verify(mockAccountService, never()).getUserByLogin(anyString());
    }

    @Test
    public void getUserByLogin_WhenWithLoginPathParam_ShouldReturnJsonUser() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        Optional<UserModel> user = Optional.of(new UserModel(id, login, name, email));

        when(mockAccountService.getUserByLogin(login)).thenReturn(user);
        
        this.mockMvc.perform(get(String.format("/users/login/%s", login)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.login").value(login))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.email").value(email));

        verify(mockAccountService, atLeastOnce()).getUserByLogin(anyString());
    }

    @Test
    public void getUserByEmail_WhenWithEmailPathParam_ShouldReturnJsonUser() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        Optional<UserModel> user = Optional.of(new UserModel(id, login, name, email));

        when(mockAccountService.getUserByEmail(email)).thenReturn(user);
        
        this.mockMvc.perform(get(String.format("/users/email/%s", email)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.login").value(login))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.email").value(email));

        verify(mockAccountService, atLeastOnce()).getUserByEmail(anyString());
    }

}