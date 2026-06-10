package mate.academy.springintro.repository;

import java.util.Optional;
import mate.academy.springintro.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.RoleName roleName);
}
