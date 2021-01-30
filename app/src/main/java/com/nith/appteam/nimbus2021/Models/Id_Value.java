package com.nith.appteam.nimbus2021.Models;

public class Id_Value {
    private String value;
    private String id;
    private String imageUrl;
    private String startTime;
    private String endTime;
    private int count;
    private int sendCount;


    public Id_Value(String value, String id, String imageUrl, String startTime, String endTime,int count,int sendCount) {
        this.value = value;
        this.id = id;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.count = count;
        this.sendCount = sendCount;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
