package com.jdziworski.skateagramservice.domain;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Entity;

/**
 * Created by kuba on 13.10.2015.
 */
@Entity
public class User extends ResourceSupport {
    private String username;
    private String password;
    private boolean enabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
