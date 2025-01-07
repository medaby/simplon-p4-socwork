package fr.medab.socwokbusiness.repos;

import fr.medab.socwokbusiness.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleUser);
    Optional<Role> findByIsFlagged(int flagged);
}
