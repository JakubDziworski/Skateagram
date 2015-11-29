package com.jdziworski.skateagramservice.domain;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by kuba on 13.10.2015.
 */
@Entity
public class User extends ResourceSupport {
    @NotNull
    @Size(min=3,max=30)
    private String username;
    @NotNull
    @Size(min=3,max=30)
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
