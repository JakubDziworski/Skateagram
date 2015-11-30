package com.kuba.skateagramclient.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by kuba on 25.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User  extends  ResourceSupport{
    private String username;
    private String password;
    private boolean enabled;

    private User() {
    }

    public User(Credentials credentials) {
        this(credentials.getPassword(),credentials.getUsername());
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        enabled = true;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}' + super.toString();
    }
}
