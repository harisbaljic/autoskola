package com.autoskola.instruktori.services.model;

import io.realm.RealmObject;

/**
 * Created by haris on 1/25/15.
 */
public class Datum  extends RealmObject{

    //date: "2015-01-20 20:02:03.000000"
    //timezone_type: 3
    //timezone: "UTC"
    private String date;
    private int timezone_type;
    private String timezone;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(int timezone_type) {
        this.timezone_type = timezone_type;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
