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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import nudemeth.poc.identity.entity.UserEntity;
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
    public void getUser_WhenNoLoginQueryParam_ShouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(get("/user"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason("Required String parameter 'login' is not present"));

        verify(mockAccountService, never()).getUser(anyString());
    }

    @Test
    public void getUser_WhenWithLoginQueryParam_ShouldReturnJsonUser() throws Exception {
        String login = "testLogin";
        String name = "Test Name";
        String userName = "Test UserName";
        String email = "Test.Email@test.com";
        UserEntity user = new UserEntity(name, userName, email);

        when(mockAccountService.getUser(login)).thenReturn(user);
        
        this.mockMvc.perform(get("/user").param("login", login))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.userName").value(userName))
            .andExpect(jsonPath("$.email").value(email));

        verify(mockAccountService, atLeastOnce()).getUser(anyString());
    }

}