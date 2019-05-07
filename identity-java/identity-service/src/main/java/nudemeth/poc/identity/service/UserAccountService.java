package nudemeth.poc.identity.service;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<UserModel> getUser(UUID id) {
        Optional<UserEntity> entity = userRepo.findById(id);
        Optional<UserModel> model = userMapper.convertToModel(entity);
        return model;
    }

    @Override
    public Optional<UserModel> getUserByLogin(String login) {
        Optional<UserEntity> entity = userRepo.findByLogin(login);
        Optional<UserModel> model = userMapper.convertToModel(entity);
        return model;
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        Optional<UserEntity> entity = userRepo.findByEmail(email);
        Optional<UserModel> model = userMapper.convertToModel(entity);
        return model;
    }

    @Override
    public UUID createUser(UserModel model) {
        UserEntity entity = userMapper.convertToEntity(model);
        UserEntity createdEntity = userRepo.save(entity);
        return createdEntity.getId();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserModel updateUser(UserModel model) {
        UserEntity entity = userMapper.convertToEntity(model);
        UserEntity updatedEntity = userRepo.save(entity);
        UserModel updatedModel = userMapper.convertToModel(updatedEntity);
        return updatedModel;
    }
}