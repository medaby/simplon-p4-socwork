package fr.medab.socwokbusiness.service;

import fr.medab.socwokbusiness.config.JwtProvider;
import fr.medab.socwokbusiness.dtos.AccountAuthenticate;
import fr.medab.socwokbusiness.dtos.AccountCreate;
import fr.medab.socwokbusiness.dtos.AuthInfo;
import fr.medab.socwokbusiness.entities.Account;
import fr.medab.socwokbusiness.entities.Role;
import fr.medab.socwokbusiness.repos.AccountRepository;
import fr.medab.socwokbusiness.repos.RoleRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AccountService {
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void create(AccountCreate inputs) {

//        if (checkEmailExists(inputs.username())) {
//            //throw new IllegalArgumentException("Email already exists");
//            throw new DataIntegrityViolationException("Username already exists");
//        }

        Account account = new Account();
        account.setUsername(inputs.username());
        account.setPassword(passwordEncoder.encode(inputs.password()));
        Role role = roleRepository.findByIsFlagged(1).orElseThrow(() -> new RuntimeException("Role not found"));
        account.setRoles(Set.of(role));
        accountRepository.save(account);
    }

    public Object login(AccountAuthenticate inputs) {
        String username = inputs.username();
        Account account = accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException(username));

        String raw = inputs.password();
        String encoded = account.getPassword();
        if (!passwordEncoder.matches(raw, encoded))
            throw new BadCredentialsException(username);

       //   if (!passwordEncoder.matches(inputs.password(), account.getPassword()))
       //   throw new BadCredentialsException(username);
       //  return jwtProvider.createToken(username, account.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
       //  return jwtProvider.createToken(username, Collections.singletonList(account.getRoles()));
        return jwtProvider.createToken(username, account.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
    }

    public AuthInfo signIn(AccountAuthenticate inputs) {
        String username = inputs.username();
        Account account = accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException(username));

        String raw = inputs.password();
        String encoded = account.getPassword();
        if (!passwordEncoder.matches(raw, encoded))
            throw new BadCredentialsException(username);

        return  new AuthInfo(
                jwtProvider.createToken(username, account.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList())),
                account.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));
    }


    private boolean checkEmailExists(String email) {
        return accountRepository.existsAccountByUsername(email);
    }

    public Account retrieveUserById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
