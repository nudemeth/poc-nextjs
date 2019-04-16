package nudemeth.poc.identity.service;

import nudemeth.poc.identity.entity.UserEntity;

public interface AccountService {
    UserEntity getUser(String login);
}