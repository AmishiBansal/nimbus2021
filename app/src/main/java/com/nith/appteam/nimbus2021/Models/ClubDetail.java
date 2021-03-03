package com.nith.appteam.nimbus2021.Models;

public class ClubDetail {

    String name;
    String imageUrl;
    int club_id;
    public ClubDetail(String name, String imageUrl,int club_id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.club_id = club_id;
    }
    public ClubDetail(String name,int club_id) {
        this.name = name;
        this.club_id = club_id;
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

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }
}