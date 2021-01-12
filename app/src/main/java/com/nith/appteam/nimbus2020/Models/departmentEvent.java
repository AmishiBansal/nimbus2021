package com.nith.appteam.nimbus2020.Models;

import java.io.Serializable;

public class departmentEvent implements Serializable {


    private static final long id = 1L;
    private String nameDEVE, infoDEVE, venueDEVE, regURLDEVE, dateDEVE, imageDEVE, abstractDEVE;

    public departmentEvent() {
    }

    public String getNameDEVE() {
        return nameDEVE;
    }

    public void setNameDEVE(String nameDEVE) {
        this.nameDEVE = nameDEVE;
    }

    public String getInfoDEVE() {
        return infoDEVE;
    }

    public void setInfoDEVE(String infoDEVE) {
        this.infoDEVE = infoDEVE;
    }

    public String getVenueDEVE() {
        return venueDEVE;
    }

    public void setVenueDEVE(String venueDEVE) {
        this.venueDEVE = venueDEVE;
    }

    public String getRegURLDEVE() {
        return regURLDEVE;
    }

    public void setRegURLDEVE(String regURLDEVE) {
        this.regURLDEVE = regURLDEVE;
    }

    public String getDateDEVE() {

        FormatDate formatDate = new FormatDate(dateDEVE);
        return formatDate.getFormattedDate();
    }

    public void setDateDEVE(String dateDEVE) {

        this.dateDEVE = dateDEVE;
    }

    public String getImageDEVE() {
        return imageDEVE;
    }

    public void setImageDEVE(String imageDEVE) {
        this.imageDEVE = imageDEVE;
    }

    public String getAbstractDEVE() {
        return abstractDEVE;
    }

    public void setAbstractDEVE(String abstractDEVE) {
        this.abstractDEVE = abstractDEVE;
    }
}
