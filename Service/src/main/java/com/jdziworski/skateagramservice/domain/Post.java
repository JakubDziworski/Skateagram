package com.jdziworski.skateagramservice.domain;

import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by kuba on 13.10.2015.
 */
public class Post extends ResourceSupport {
    private Date date;
    private String userId;
    private String spotId;
    private String trickId;
    private String videoId;

    public Post() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String getTrickId() {
        return trickId;
    }

    public void setTrickId(String trickId) {
        this.trickId = trickId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
