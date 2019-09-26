package nudemeth.poc.identity.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import nudemeth.poc.identity.exception.InvalidTokenException;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.service.AccountService;

@RestController
public class AccountController {

    private AccountService accountService;

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Async("asyncExecutor")
    @GetMapping(path = "/users/login/{login}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<Optional<UserModel>> getUserByLogin(@PathVariable(required = true) String login, @RequestParam(required = false) String issuer) {
        return accountService.getUserByLoginAndIssuer(login, issuer);
    }

    @Async("asyncExecutor")
    @GetMapping(path = "/users/email/{email}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<Optional<UserModel>> getUserByEmail(@PathVariable(required = true) String email) {
        return accountService.getUserByEmail(email);
    }

    @Async("asyncExecutor")
    @GetMapping(path = "/users/{id}/token", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<Optional<String>> getTokenByUserId(@PathVariable(required = true) String id) {
        UUID uuid = getUuidFromString(id);
        return accountService.getTokenByUserId(uuid).exceptionally(ex -> {
            logger.error(ex.getMessage(), ex);
            if (ex.getCause() != null && ex.getCause() instanceof InvalidTokenException) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        });
    }

    @Async("asyncExecutor")
    @GetMapping(path = "/users/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<Optional<UserModel>> getUser(@PathVariable(required = true) String id) {
        UUID uuid = getUuidFromString(id);
        return accountService.getUser(uuid);
    }

    @Async("asyncExecutor")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/users", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<UUID> createUser(@RequestBody UserModel model) {
        return accountService.createUser(model);
    }

    @Async("asyncExecutor")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PutMapping(path = "/users/issuer/{issuer}/code/{code}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<UUID> createOrUpdateIssuerUser(@PathVariable(required = true) String issuer, @PathVariable(required = true) String code) {
        if (issuer == null || issuer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Issuer is required");
        }
        if (code == null || code.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Code is required for issuer: %s", issuer));
        }
        return accountService.createOrUpdateIssuerUser(issuer, code);
    }

    @Async("asyncExecutor")
    @PutMapping(path = "/users/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public CompletableFuture<UserModel> updateUser(@PathVariable(required = true) String id, @RequestBody UserModel model) {
        UUID uuid = getUuidFromString(id);
        if (!uuid.equals(model.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid updating id: %s and %s", id, model.getId().toString()));
        }
        return accountService.updateUser(model);
    }

    @Async("asyncExecutor")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/users/{id}")
    public CompletableFuture<Void> deleteUser(@PathVariable(required = true) String id) {
        UUID uuid = getUuidFromString(id);
        return accountService.deleteUser(uuid);
    }

    private UUID getUuidFromString(String id) throws ResponseStatusException {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}