package nudemeth.poc.identity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.mapper.UserMapper;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.repository.UserRepository;

@Service
public class UserAccountService implements AccountService {

    private UserRepository userRepo;
    private UserMapper userMapper;

    @Autowired
    public UserAccountService(UserRepository userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public UserModel getUserFromLogin(String login) {
        UserEntity entity = userRepo.findByLogin(login);
        UserModel model = userMapper.convertToModel(entity);
        return model;
    }

}