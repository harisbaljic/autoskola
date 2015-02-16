package com.autoskola.instruktori.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by The Boss on 29.12.2014.
 */
public class Helper {

    public static String login = "http://projekt001.app.fit.ba/autoskola/servis_Login.php";
    public static String parseDateForObavijesti(String dbFormat) {
        Date temp;
        try {
            temp = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dbFormat);
            DateFormat df = new SimpleDateFormat("d.M.yyyy H:mm");

            // Get the date today using Calendar object.

            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            String reportDate = df.format(temp);

            // Print what date is today!

            return reportDate;
        } catch (ParseException e) {
            return "null";
        }

    }
}
