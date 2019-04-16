package nudemeth.poc.identity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.service.IAccountService;
import nudemeth.poc.identity.viewmodel.LoginViewModel;

@RestController
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping(path = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserEntity getUser(@RequestParam(value = "login", required = true) String login) {
        return accountService.getUser(login);
    }

    @PostMapping(path = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String login(@RequestBody LoginViewModel model) {
        return "";
    }
}