package com.nith.appteam.nimbus2020.Models;

public class TeamMember {

    String name;
    String imageUrl;
    String designation;

    public TeamMember(String name, String imageUrl, String designation) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
}