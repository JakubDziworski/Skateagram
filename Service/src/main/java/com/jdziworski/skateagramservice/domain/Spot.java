package com.jdziworski.skateagramservice.domain;

import org.springframework.hateoas.ResourceSupport;

import java.awt.*;

/**
 * Created by kuba on 13.10.2015.
 */
public class Spot extends ResourceSupport {
   private String name;

    public Spot() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
