package com.nith.appteam.nimbus2021.Models;

public class GalleryDetail {

    String name;
    String imageUrl;
    int year;
    public GalleryDetail(String name, String imageUrl, int year) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.year = year;
    }
    public GalleryDetail(String name, int year) {
        this.name = name;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}