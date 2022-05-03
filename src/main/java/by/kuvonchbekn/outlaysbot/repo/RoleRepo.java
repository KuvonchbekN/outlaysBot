package by.kuvonchbekn.outlaysbot.repo;


import by.kuvonchbekn.outlaysbot.entity.Role;
import by.kuvonchbekn.outlaysbot.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleType(RoleType roleType);
    boolean existsByRoleType(RoleType roleType);
}
