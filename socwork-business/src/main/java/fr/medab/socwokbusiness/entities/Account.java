package fr.medab.socwokbusiness.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Entity
@Table(name = "t_accounts")
public class Account extends AbstractEntity {

    @Column(name = "username", length = 255, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER  , cascade = CascadeType.PERSIST)
    @JoinTable( name = "t_accounts_roles",
            joinColumns = @JoinColumn( name = "id_account" ),
            inverseJoinColumns = @JoinColumn( name = "id_role" ))
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
//        return authorities;
//    }

    @Override
    public String toString() {
        // Do not print password junior!
        return String.format("Account{username='%s', password=[PROTECTED]}, roles=[LAZY_LOADED]", username);
    }

    @Override
    public int hashCode() {

        int result = 1;
        result = 31 * result + username.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username);
    }
    // return obj instanceof Country other && code.equals(other.code);
}
