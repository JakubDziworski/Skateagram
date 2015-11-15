package com.jdziworski.skateagramservice.domain;

import java.util.Date;

/**
 * Created by kuba on 13.10.2015.
 */
public class Comment {
    private String message;
    private Date date;
    private int userId;

    public Comment() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
