package com.nith.appteam.nimbus2020.Models;

public class CoreTeamModel {

    String name;
    String imageUrl;
    String position;

    public CoreTeamModel(String name, String imageUrl, String position) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}