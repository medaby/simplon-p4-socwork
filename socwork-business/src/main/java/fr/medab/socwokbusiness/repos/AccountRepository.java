package fr.medab.socwokbusiness.repos;

import fr.medab.socwokbusiness.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsAccountByUsername(String username);

    Optional<Account> findByUsernameIgnoreCase(String username); // or null
}
