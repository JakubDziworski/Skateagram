package com.kuba.skateagramclient.domain;

public class PostBuilder {
    private String videoURL;
    private String trickName;
    private String spotName;

    public PostBuilder setVideoURL(String videoURL) {
        this.videoURL = videoURL;
        return this;
    }

    public PostBuilder setTrickName(String trickName) {
        this.trickName = trickName;
        return this;
    }

    public PostBuilder setSpotName(String spotName) {
        this.spotName = spotName;
        return this;
    }

    public Post createPost() {
        return new Post(videoURL, trickName, spotName);
    }
}