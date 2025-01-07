package fr.medab.socwokbusiness.entities;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_roles")
public class Role extends AbstractEntity {

    @Column(name = "role_name", length = 255)
    private String roleName ="ROLE_MANAGER";

    @Column(name = "flag")
    private int isFlagged;

//    @ManyToMany
//    @JoinTable( name = "t_accounts_roles",
//            joinColumns = @JoinColumn( name = "id_role" ),
//            inverseJoinColumns = @JoinColumn( name = "id_account" ) )
//    private Set<Account> accounts = new HashSet<>();

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int isFlagged() {
        return isFlagged;
    }

    public void setFlagged(int flagged) {
        isFlagged = flagged;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(roleName, role.roleName);
    }
}
