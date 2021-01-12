package com.nith.appteam.nimbus2020.Models;

import java.io.Serializable;

public class instituteEvent implements Serializable {

    private static final long id = 1L;
    private String nameIEVE, infoIEVE, venueIEVE, regURLIEVE, dateIEVE, imageIEVE, abstractIEVE;


    public instituteEvent() {
    }

    public String getNameIEVE() {
        return nameIEVE;
    }

    public void setNameIEVE(String nameIEVE) {
        this.nameIEVE = nameIEVE;
    }

    public String getInfoIEVE() {
        return infoIEVE;
    }

    public void setInfoIEVE(String infoIEVE) {
        this.infoIEVE = infoIEVE;
    }

    public String getVenueIEVE() {
        return venueIEVE;
    }

    public void setVenueIEVE(String venueIEVE) {
        this.venueIEVE = venueIEVE;
    }

    public String getRegURLIEVE() {
        return regURLIEVE;
    }

    public void setRegURLIEVE(String regURLIEVE) {
        this.regURLIEVE = regURLIEVE;
    }

    public String getDateIEVE() {
        FormatDate formatDate = new FormatDate(dateIEVE);
        return formatDate.getFormattedDate();
    }

    public void setDateIEVE(String dateIEVE) {
        this.dateIEVE = dateIEVE;
    }

    public String getImageIEVE() {
        return imageIEVE;
    }

    public void setImageIEVE(String imageIEVE) {
        this.imageIEVE = imageIEVE;

    }

    public String getAbstractIEVE() {
        return abstractIEVE;
    }

    public void setAbstractIEVE(String abstractIEVE) {
        this.abstractIEVE = abstractIEVE;
    }
}
