package com.nith.appteam.nimbus2020.Models;

public class FormatDate {
    String date;

    public FormatDate(String date) {
        this.date = date;
    }

    public String getFormattedDate() {
        String month = date.substring(5, 7);
        String monthName = "March";
        String day = date.substring(8, 10);
        if (month.equals("01"))
            monthName = "Jan";
        else if (month.equals("02"))
            monthName = "Feb";
        else if (month.equals("03"))
            monthName = "March";
        else if (month.equals("04"))
            monthName = "April";
        else if (month.equals("05"))
            monthName = "May";
        else if (month.equals("06"))
            monthName = "June";
        else if (month.equals("07"))
            monthName = "July";
        else if (month.equals("08"))
            monthName = "Aug";
        else if (month.equals("09"))
            monthName = "Sep";
        else if (month.equals("10"))
            monthName = "Oct";
        else if (month.equals("11"))
            monthName = "Nov";
        else if (month.equals("12"))
            monthName = "Dec";
        int hour = Integer.parseInt(date.substring(11, 13));
        String min = date.substring(13, 16);
        String info;
        if (hour > 12) {
            info = "PM";
            hour = hour - 12;
        } else
            info = "AM";
        return "On: " + day + " " + monthName + " " + hour + min + " " + info;
    }
}
