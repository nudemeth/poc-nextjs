package nudemeth.poc.identity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.repository.UserRepository;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private UserRepository userRepo;

    public UserEntity getUser(String login) {
        return userRepo.findByUserName(login);
    }

}