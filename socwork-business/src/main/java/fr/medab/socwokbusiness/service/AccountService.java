package fr.medab.socwokbusiness.service;

import fr.medab.socwokbusiness.config.JwtProvider;
import fr.medab.socwokbusiness.dtos.AccountAuthenticate;
import fr.medab.socwokbusiness.dtos.AccountCreate;
import fr.medab.socwokbusiness.entities.Account;
import fr.medab.socwokbusiness.repos.AccountRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public void create(AccountCreate inputs) {

        if (checkEmailExists(inputs.username())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Account account = new Account();
        account.setUsername(inputs.username());
        account.setPassword(passwordEncoder.encode(inputs.password()));
        accountRepository.save(account);
    }

    public Object login(AccountAuthenticate credentials) {
        String username = credentials.username();
        Account account = accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException(username));

        if (!passwordEncoder.matches(credentials.password(), account.getPassword()))
            throw new BadCredentialsException(username);

        return jwtProvider.createToken(username);
    }

    private boolean checkEmailExists(String email) {
        return accountRepository.existsAccountByUsername(email);
    }

}
