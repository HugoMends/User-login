package com.hugodev.user_login.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private Set<String> roles = new HashSet<>();

    public UserUpdateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}