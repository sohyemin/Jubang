package HelloWorld.Jubang.domain.user.repository;

import HelloWorld.Jubang.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(attributePaths = {"userRoleList"})
    @Query("select u from User u where u.email = :email")
    Optional<User> getWithRoles(@Param("email") String email);

    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("delete from User u where u.email = :email")
    void deleteByEmail(@Param("email") String email);
}
