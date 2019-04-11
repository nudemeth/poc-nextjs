package nudemeth.poc.identity.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nudemeth.poc.identity.viewmodel.LoginViewModel;

@RestController
public class AccountController {

    public AccountController() {

    }

    @GetMapping(path = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getUser(@RequestParam(value = "login", required = true) String login) {
        return "";
    }

    @PostMapping(path = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String login(@RequestBody LoginViewModel model) {
        return "";
    }
}