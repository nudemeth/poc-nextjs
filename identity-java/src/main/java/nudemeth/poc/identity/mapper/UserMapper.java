package nudemeth.poc.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import nudemeth.poc.identity.entity.UserEntity;
import nudemeth.poc.identity.model.UserModel;
import nudemeth.poc.identity.service.AccountService;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = AccountService.class)
public interface UserMapper {
    UserModel mapToModel(UserEntity entity);
    UserEntity mapToEntity(UserModel model);
}