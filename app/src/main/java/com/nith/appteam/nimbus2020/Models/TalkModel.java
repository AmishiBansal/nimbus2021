package com.nith.appteam.nimbus2020.Models;

import java.io.Serializable;

public class TalkModel
        implements Serializable {
    private static final long id = 1L;
    private String name;
    private String info;
    private String venue;
    private String regURL;
    private String date;
    private String image;


    private String idTalk;

    public TalkModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getRegURL() {
        return regURL;
    }

    public void setRegURL(String regURL) {
        this.regURL = regURL;
    }

    public String getDate() {
        FormatDate formatDate = new FormatDate(date);
        return formatDate.getFormattedDate();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdTalk() {
        return idTalk;
    }

    public void setIdTalk(String idTalk) {
        this.idTalk = idTalk;
    }


}



