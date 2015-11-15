package com.jdziworski.skateagramservice.domain;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by kuba on 13.10.2015.
 */
public class Trick extends ResourceSupport {
    String url;

    public Trick() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
