package nudemeth.poc.identity.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.service.CipherService;;

@Component
public class UserMapper implements Mapper<UserModel, UserEntity> {

    private CipherService cipherService;

    @Autowired
    public UserMapper(CipherService cipherService) {
        this.cipherService = cipherService;
    }

    @Override
    public UserEntity convertToEntity(UserModel model) {
        if (model == null) {
            return null;
        }
        String encryptedToken = null;
        if (model.getIssuerToken() != null) {
            encryptedToken = cipherService.encrypt(model.getIssuerToken());
        }
        return new UserEntity(model.getId(), model.getLogin(), model.getIssuer(), encryptedToken, model.getName(), model.getEmail(), model.isEmailConfirmed());
    }

    @Override
    public UserModel convertToModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        String decryptedToken = null;
        if (entity.getIssuerToken() != null) {
            decryptedToken = cipherService.decrypt(entity.getIssuerToken());
        }
        return new UserModel(entity.getId(), entity.getLogin(), entity.getIssuer(), decryptedToken, entity.getName(), entity.getEmail(), entity.isEmailConfirmed());
    }

    @Override
    public Optional<UserEntity> convertToEntity(Optional<UserModel> model) {
        return model.map(m -> convertToEntity(m));
    }

    @Override
    public Optional<UserModel> convertToModel(Optional<UserEntity> entity) {
        return entity.map(e -> convertToModel(e));
    }
}