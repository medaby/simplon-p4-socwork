package fr.medab.socwokbusiness.controller;

import fr.medab.socwokbusiness.dtos.AccountCreate;
import fr.medab.socwokbusiness.dtos.AccountAuthenticate;
import fr.medab.socwokbusiness.dtos.AuthInfo;
import fr.medab.socwokbusiness.entities.Account;
import fr.medab.socwokbusiness.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody AccountCreate inputs) {
        accountService.create(inputs);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    Object login(@RequestBody AccountAuthenticate inputs) {
        return accountService.login(inputs);
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    String testAccess() {
        return "tu es authentifié";
    }

    @GetMapping("/with-role")
    @ResponseStatus(HttpStatus.OK)
    String withRole() {
        return "with role";
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Account retrieveUserById(@PathVariable Long id) {
        return accountService.retrieveUserById(id);
    }


    @PostMapping("/sign-in")
    AuthInfo signIn(@RequestBody @Valid AccountAuthenticate inputs){
        return accountService.signIn(inputs);
    }
}
