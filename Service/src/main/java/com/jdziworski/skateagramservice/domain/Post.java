package com.jdziworski.skateagramservice.domain;

import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by kuba on 13.10.2015.
 */
public class Post extends ResourceSupport {
    private Date date;
    private int userId;
    private int spotId;
    private int trickId;
    private int videoId;

    public Post() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public int getTrickId() {
        return trickId;
    }

    public void setTrickId(int trickId) {
        this.trickId = trickId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
}
