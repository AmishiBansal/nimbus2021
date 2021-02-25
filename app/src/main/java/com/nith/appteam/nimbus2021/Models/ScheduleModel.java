package com.nith.appteam.nimbus2021.Models;

import java.io.Serializable;

public class ScheduleModel implements Serializable {
    private String nameSch, venueSch, timeSch, deptSch, imagSch;

    public ScheduleModel() {
    }

    public String getNameSch() {
        return nameSch;
    }

    public void setNameSch(String nameSch) {
        this.nameSch = nameSch;
    }

    public String getVenueSch() {
        return venueSch;
    }

    public void setVenueSch(String venueSch) {
        this.venueSch = venueSch;
    }

    public String getTimeSch() {
        return timeSch;
    }

    public void setTimeSch(String timeSch) {
        this.timeSch = timeSch;
    }

    public String getDeptSch() {
        return deptSch;
    }

    public void setDeptSch(String deptSch) {
        this.deptSch = deptSch;
    }

    public String getImagSch() {
        return imagSch;
    }

    public void setImagSch(String imagSch) {
        this.imagSch = imagSch;
    }
}
