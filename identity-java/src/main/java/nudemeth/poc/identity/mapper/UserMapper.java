package nudemeth.poc.identity.mapper;

import org.springframework.stereotype.Component;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.model.UserModel;

@Component
public class UserMapper implements Mapper<UserModel, UserEntity> {

    @Override
    public UserEntity convertToEntity(UserModel model) {
        if (model == null) {
            return null;
        }
        return new UserEntity(
            model.getId(),
            model.getLogin(),
            model.getName(),
            model.getEmail()
        );
    }

    @Override
    public UserModel convertToModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserModel(
            entity.getId(),
            entity.getLogin(),
            entity.getName(),
            entity.getEmail()
        );
    }
}