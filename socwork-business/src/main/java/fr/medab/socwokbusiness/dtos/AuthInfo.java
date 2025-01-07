package fr.medab.socwokbusiness.dtos;

import org.springframework.lang.NonNull;

import java.util.Set;

public record AuthInfo(
        String token, Set<String> roles) {
    @Override
    public String toString() {
        return String.format("AuthInfo{token=[PROTECTED], role='%d'}", roles.size());
    }
}
