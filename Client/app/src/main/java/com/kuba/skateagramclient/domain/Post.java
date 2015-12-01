package com.kuba.skateagramclient.domain;

/**
 * Created by kuba on 21.11.2015.
 */
public class Post extends ResourceSupport {
    String videoId;
    String trickId;
    String spotId;

    String userId;
    String date;

    public Post() {
    }

    public Post(String videoURL, String trickId, String spotId) {
        this.videoId = videoURL;
        this.trickId = trickId;
        this.spotId = spotId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTrickId() {
        return trickId;
    }

    public String getSpotId() {
        return spotId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }
}
