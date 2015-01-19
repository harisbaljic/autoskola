package com.autoskola.instruktori.model;

public class Datum {
    private String date, timezone;
    private int timezone_type;

    public Datum(String date, String timezone, int timezone_type) {
        this.date = date;
        this.timezone = timezone;
        this.timezone_type = timezone_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(int timezone_type) {
        this.timezone_type = timezone_type;
    }

}