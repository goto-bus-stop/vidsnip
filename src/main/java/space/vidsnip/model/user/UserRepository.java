package space.vidsnip.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(@Param("username") String username);
    List<User> findByUsernameIn(@Param("username") List<String> usernames);
    Optional<User> findByEmail(@Param("email") String email);
}
