package com.nith.appteam.nimbus2020.Models;

import java.io.Serializable;

public class ExhibitionModel
        implements Serializable {


    private static final long id = 1L;
    private String nameExh, infoExh, venueExh, regURLExh, dateExh, imageExh;

    public ExhibitionModel() {
    }

    public String getNameExh() {
        return nameExh;
    }

    public void setNameExh(String nameExh) {
        this.nameExh = nameExh;
    }

    public String getInfoExh() {
        return infoExh;
    }

    public void setInfoExh(String infoExh) {
        this.infoExh = infoExh;
    }

    public String getVenueExh() {
        return venueExh;
    }

    public void setVenueExh(String venueExh) {
        this.venueExh = venueExh;
    }

    public String getRegURLExh() {
        return regURLExh;
    }

    public void setRegURLExh(String regURLExh) {
        this.regURLExh = regURLExh;
    }

    public String getDateExh() {
        FormatDate formatDate = new FormatDate(dateExh);
        return formatDate.getFormattedDate();
    }

    public void setDateExh(String dateExh) {
        this.dateExh = dateExh;
    }

    public String getImageExh() {
        return imageExh;
    }

    public void setImageExh(String imageExh) {
        this.imageExh = imageExh;
    }
}