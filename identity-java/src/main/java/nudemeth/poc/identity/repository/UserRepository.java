package nudemeth.poc.identity.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nudemeth.poc.identity.entity.UserEntity;;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByLogin(String login);
    List<UserEntity> findByEmail(String email);
}