package nudemeth.poc.identity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.mapper.UserMapper;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.repository.UserRepository;

@Service
public class UserAccountService implements AccountService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserMapper userMapper;

    public UserModel getUser(String login) {
        UserEntity entity = userRepo.findByLogin(login);
        UserModel model = userMapper.convertToModel(entity);
        return model;
    }

}