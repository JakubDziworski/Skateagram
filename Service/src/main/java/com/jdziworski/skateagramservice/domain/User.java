package com.jdziworski.skateagramservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.management.resource.ResourceRequest;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by kuba on 13.10.2015.
 */
public class User extends ResourceSupport {
    private String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
