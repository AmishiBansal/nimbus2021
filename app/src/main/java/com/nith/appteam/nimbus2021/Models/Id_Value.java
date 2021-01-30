package com.nith.appteam.nimbus2021.Models;

public class Id_Value {
    private String value;
    private String id;
    private String imageUrl;
    private String startTime;
    private String endTime;
    private String count;


    public Id_Value(String value, String id, String imageUrl, String startTime, String endTime,String count) {
        this.value = value;
        this.id = id;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.count = count;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
