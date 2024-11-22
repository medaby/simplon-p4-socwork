package fr.medab.socwokbusiness.controller;

import fr.medab.socwokbusiness.dtos.AccountCreate;
import fr.medab.socwokbusiness.dtos.AccountAuthenticate;
import fr.medab.socwokbusiness.service.AccountService;
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
    Object login(@RequestBody AccountAuthenticate credentials) {
        return accountService.login(credentials);
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    String testAccess() {
        return "tu es authentifié";
    }
}
