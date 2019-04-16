package nudemeth.poc.identity.service;

import nudemeth.poc.identity.entity.UserEntity;

public interface IAccountService {
    UserEntity getUser(String login);
}