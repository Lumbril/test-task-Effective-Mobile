package effective.mobile.code.repositories;

import effective.mobile.code.entities.Role;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
