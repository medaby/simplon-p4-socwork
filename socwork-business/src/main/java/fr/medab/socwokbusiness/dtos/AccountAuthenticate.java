package fr.medab.socwokbusiness.dtos;

public record AccountAuthenticate(String username, String password) {

    @Override
    public String toString() {
        // Do not print password junior!
        return String.format("Account{username='%s', password=[PROTECTED]}", username);
    }
}
