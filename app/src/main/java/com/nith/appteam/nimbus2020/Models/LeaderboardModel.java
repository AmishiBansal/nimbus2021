package com.nith.appteam.nimbus2020.Models;

public class LeaderboardModel {

    private String name;
    private int score;
    private String imageUrl;


    public LeaderboardModel(String name, int score, String imageUrl) {
        this.name = name;
        this.score = score;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
