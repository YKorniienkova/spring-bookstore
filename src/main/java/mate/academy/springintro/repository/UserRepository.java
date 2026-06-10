package mate.academy.springintro.repository;

import java.util.Optional;
import mate.academy.springintro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    @Query(""" 
            FROM User u
            LEFT JOIN FETCH u.roles
            WHERE u.email = :email
            """)
    Optional<User> findByEmail(String email);
}
