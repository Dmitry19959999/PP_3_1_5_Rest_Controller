package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;


@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String roleName;

    public Role() {

    }

    public Role(long id) {
        this.id = id;
    }
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String role) {
        this.roleName = role;
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }
    @Override
    public String toString() {
        return this.roleName;
    }
}