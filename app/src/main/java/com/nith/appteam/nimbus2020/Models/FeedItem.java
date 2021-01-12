package com.nith.appteam.nimbus2020.Models;

public class FeedItem {
    private String imageUrl;
    private String socialUrl;

    public FeedItem(String imageUrl, String socialUrl) {
        this.imageUrl = imageUrl;
        this.socialUrl = socialUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSocialUrl() {
        return socialUrl;
    }

    public void setSocialUrl(String socialUrl) {
        this.socialUrl = socialUrl;
    }

}
