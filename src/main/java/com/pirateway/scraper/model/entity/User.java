package com.pirateway.scraper.model.entity;

import com.pirateway.scraper.enumerate.Role;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
public class User extends AbstractEntity {

    public User() {
    }

    public User(
            @Nullable final String login,
            @Nullable final String password,
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final Role role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.description = description;
        this.role = role;
    }

    @Nullable
    @Column(unique = true)
    private String login = "";

    @Nullable
    private String password = "";

    @Nullable
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    @Nullable
    public String getLogin() {
        return login;
    }

    public void setLogin(@Nullable String login) {
        this.login = login;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    @Nullable
    public Role getRole() {
        return role;
    }

    public void setRole(@Nullable Role role) {
        this.role = role;
    }

}
