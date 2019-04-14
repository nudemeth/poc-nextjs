package nudemeth.poc.identity.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import nudemeth.poc.identity.entity.User;

public interface UserRepository extends CrudRepository<User, UUID> {
    List<User> findByUserName(String userName);
    List<User> findByEmail(String email);
}