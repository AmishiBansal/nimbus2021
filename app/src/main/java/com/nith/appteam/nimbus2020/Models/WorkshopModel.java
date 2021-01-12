package com.nith.appteam.nimbus2020.Models;

import java.io.Serializable;

public class WorkshopModel implements Serializable {
    private String nameWor, infoWor, venueWor, dateWor, imageWor, UrlWor, typeWor;
    private Serializable id = "1K";

    public WorkshopModel() {
    }

    public String getNameWor() {
        return nameWor;
    }

    public void setNameWor(String nameWor) {
        this.nameWor = nameWor;
    }

    public String getInfoWor() {
        return infoWor;
    }

    public void setInfoWor(String infoWor) {
        this.infoWor = infoWor;
    }

    public String getVenueWor() {
        return venueWor;
    }

    public void setVenueWor(String venueWor) {
        this.venueWor = venueWor;
    }

    public String getDateWor() {
        FormatDate formatDate = new FormatDate(dateWor);
        return formatDate.getFormattedDate();
    }

    public void setDateWor(String dateWor) {
        this.dateWor = dateWor;
    }

    public String getImageWor() {
        return imageWor;
    }

    public void setImageWor(String imageWor) {
        this.imageWor = imageWor;
    }

    public String getUrlWor() {
        return UrlWor;
    }

    public void setUrlWor(String urlWor) {
        UrlWor = urlWor;
    }

    public String getTypeWor() {
        return typeWor;
    }

    public void setTypeWor(String typeWor) {
        this.typeWor = typeWor;
    }
}
