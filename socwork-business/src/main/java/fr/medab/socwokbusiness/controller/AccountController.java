package fr.medab.socwokbusiness.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @PostMapping
    public String createAccount() {
        return "login";
    }
}
