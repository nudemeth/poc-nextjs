package nudemeth.poc.identity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import nudemeth.poc.identity.model.TokenModel;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AccountService mockAccountService;

    @Test
    public void getUser_WhenNoIdPathParam_ShouldReturnMethodNotAllowed() throws Exception {
        this.mockMvc.perform(get("/users"))
            .andDo(print())
            .andExpect(status().isMethodNotAllowed())
            .andExpect(status().reason("Request method 'GET' not supported"));

        verify(mockAccountService, never()).getUser(any());
    }

    @Test
    public void getUser_WhenWithIdPathParam_ShouldReturnJsonUser() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        Optional<UserModel> user = Optional.of(new UserModel(id, login, issuer, token, name, email, isEmailConfirmed));

        when(mockAccountService.getUser(id)).thenReturn(CompletableFuture.completedFuture(user));
        
        MvcResult asyncResult = this.mockMvc.perform(get(String.format("/users/%s", id.toString())))
            .andExpect(request().asyncStarted())
            .andReturn();

        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.login").value(login))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.email").value(email));

        verify(mockAccountService, atLeastOnce()).getUser(id);
    }

    @Test
    public void getTokenByUser_WhenWithIdPathParam_ShouldReturnJson() throws Exception {
        UUID id = UUID.randomUUID();
        String token = "Test.Token";
        Optional<TokenModel> userToken = Optional.of(new TokenModel(id, token));

        when(mockAccountService.getTokenByUserId(id)).thenReturn(CompletableFuture.completedFuture(userToken));
        
        MvcResult asyncResult = this.mockMvc.perform(get(String.format("/token/user/%s", id.toString())))
            .andExpect(request().asyncStarted())
            .andReturn();

        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.token").value(token));

        verify(mockAccountService, atLeastOnce()).getTokenByUserId(id);
    }

    @Test
    public void getUser_WhenWithInvalidIdPathParam_ShouldReturnBadRequest() throws Exception {
        String id = "some-invalid-uuid";
        
        MvcResult asyncResult = this.mockMvc.perform(get(String.format("/users/%s", id)))
            .andExpect(request().asyncStarted())
            .andReturn();

        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(String.format("Invalid UUID string: %s", id)));

        verify(mockAccountService, never()).getUser(any());
    }

    @Test
    public void getUserByLogin_WhenWithLoginPathParam_ShouldReturnJsonUser() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        Optional<UserModel> user = Optional.of(new UserModel(id, login, issuer, token, name, email, isEmailConfirmed));

        when(mockAccountService.getUserByLogin(login)).thenReturn(CompletableFuture.completedFuture(user));
        
        MvcResult asyncResult = this.mockMvc.perform(get(String.format("/users/login/%s", login)))
            .andExpect(request().asyncStarted())
            .andReturn();
        
        this.mockMvc.perform(asyncDispatch(asyncResult))
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
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        Optional<UserModel> user = Optional.of(new UserModel(id, login, issuer, token, name, email, isEmailConfirmed));

        when(mockAccountService.getUserByEmail(email)).thenReturn(CompletableFuture.completedFuture(user));
        
        MvcResult asyncResult = this.mockMvc.perform(get(String.format("/users/email/%s", email)))
            .andExpect(request().asyncStarted())
            .andReturn();

        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.login").value(login))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.email").value(email));

        verify(mockAccountService, atLeastOnce()).getUserByEmail(anyString());
    }

    @Test
    public void createUser_WhenWithUserModel_ShouldReturnUUIDWithCreatedHttpStatus() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        UserModel user = new UserModel(login);
        user.setName(name);
        user.setEmail(email);
        String jsonUser = mapper.writeValueAsString(user);
        
        when(mockAccountService.createUser(user)).thenReturn(CompletableFuture.completedFuture(id));
        
        MvcResult asyncResult = this.mockMvc.perform(
                post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonUser)
            )
            .andExpect(request().asyncStarted())
            .andReturn();
        
        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(mapper.writeValueAsString(id)));

        verify(mockAccountService, atLeastOnce()).createUser(user);
    }

    @Test
    public void createUser_WhenNoUserModel_ShouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(
                post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
            )
            .andDo(print())
            .andExpect(status().isBadRequest());

        verify(mockAccountService, never()).createUser(any());
    }

    @Test
    public void updateUser_WhenNoIdPathParam_ShouldReturnMethodNotAllowed() throws Exception {
        this.mockMvc.perform(
                put("/users")
            )
            .andDo(print())
            .andExpect(status().isMethodNotAllowed())
            .andExpect(status().reason("Request method 'PUT' not supported"));

        verify(mockAccountService, never()).updateUser(any());
    }

    @Test
    public void updateUser_WhenWithIdAndUserModel_ShouldReturnUpdatedModel() throws Exception {
        UUID id = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        UserModel updatingUser = new UserModel(id, login, issuer, token, name, email, isEmailConfirmed);
        UserModel updatedUser = new UserModel(id, login, issuer, token, name, email, isEmailConfirmed);
        String jsonUser = mapper.writeValueAsString(updatingUser);
        
        when(mockAccountService.updateUser(updatingUser)).thenReturn(CompletableFuture.completedFuture(updatedUser));
        
        MvcResult asyncResult = this.mockMvc.perform(
                put(String.format("/users/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonUser)
            )
            .andExpect(request().asyncStarted())
            .andReturn();

        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.login").value(login))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.email").value(email));

        verify(mockAccountService, atLeastOnce()).updateUser(updatingUser);
    }

    @Test
    public void updateUser_WhenPathIdAndModelIdNotMatched_ShouldReturnBadRequest() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        UserModel updatingUser = new UserModel(id1, login, issuer, token, name, email, isEmailConfirmed);
        String jsonUser = mapper.writeValueAsString(updatingUser);
        
        MvcResult asyncResult = this.mockMvc.perform(
                put(String.format("/users/%s", id2.toString()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonUser)
            )
            .andExpect(request().asyncStarted())
            .andReturn();
        
        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(String.format("Invalid updating id: %s and %s", id2.toString(), id1.toString())));

        verify(mockAccountService, never()).updateUser(updatingUser);
    }

    @Test
    public void updateUser_WhenWithInvalidIdPathParam_ShouldReturnBadRequest() throws Exception {
        String id = "some-invalid-uuid";
        UUID uuid = UUID.randomUUID();
        String login = "testLogin";
        String name = "Test Name";
        String email = "Test.Email@test.com";
        String issuer = "Test Issuer";
        String token = "abc";
        boolean isEmailConfirmed = false;
        UserModel updatingUser = new UserModel(uuid, login, issuer, token, name, email, isEmailConfirmed);
        String jsonUser = mapper.writeValueAsString(updatingUser);

        MvcResult asyncResult = this.mockMvc.perform(
                put(String.format("/users/%s", id))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonUser)
            )
            .andExpect(request().asyncStarted())
            .andReturn();
            
        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(String.format("Invalid UUID string: %s", id)));

        verify(mockAccountService, never()).updateUser(any());
    }

    @Test
    public void deleteUser_WhenNoLoginPathParam_ShouldReturnMethodNotAllowed() throws Exception {
        this.mockMvc.perform(
                delete("/users")
            )
            .andDo(print())
            .andExpect(status().isMethodNotAllowed())
            .andExpect(status().reason("Request method 'DELETE' not supported"));

        verify(mockAccountService, never()).deleteUser(any());
    }

    @Test
    public void deleteUser_WhenWithIdPathParam_ShouldReturnJsonUser() throws Exception {
        UUID id = UUID.randomUUID();

        MvcResult asyncResult = this.mockMvc.perform(
                delete(String.format("/users/%s", id.toString()))
            )
            .andExpect(request().asyncStarted())
            .andReturn();
            
        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isNoContent());

        verify(mockAccountService, atLeastOnce()).deleteUser(id);
    }

    @Test
    public void deleteUser_WhenWithInvalidIdPathParam_ShouldReturnBadRequest() throws Exception {
        String id = "some-invalid-uuid";
        
        MvcResult asyncResult = this.mockMvc.perform(
                delete(String.format("/users/%s", id))
            )
            .andExpect(request().asyncStarted())
            .andReturn();

        this.mockMvc.perform(asyncDispatch(asyncResult))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(String.format("Invalid UUID string: %s", id)));

        verify(mockAccountService, never()).deleteUser(any());
    }

}