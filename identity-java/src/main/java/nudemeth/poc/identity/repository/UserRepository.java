package nudemeth.poc.identity.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import nudemeth.poc.identity.entity.UserEntity;;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByLogin(String login);
    List<UserEntity> findByEmail(String email);
}