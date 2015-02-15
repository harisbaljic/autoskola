package com.autoskola.instruktori.helpers;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by The Boss on 29.12.2014.
 */
public class Helper {


    public static String getTop10Obavijesti = "http://projekt001.app.fit.ba/autoskola/servis_ObavijestiTop10.php";
    public static String login = "http://projekt001.app.fit.ba/autoskola/servis_Login.php";
    public static String slike = "http://projekt001.app.fit.ba/autoskola/servis_Slike.php";
    public static String gradovi = "http://projekt001.app.fit.ba/autoskola/servis_SelectGradoviAll.php";
    public static String instruktori = "http://projekt001.app.fit.ba/autoskola/servis_SelectInstruktoriAll.php";
    public static String uplate = "http://projekt001.app.fit.ba/autoskola/servis_Uplate.php";
    public static String prijava = "http://projekt001.app.fit.ba/autoskola/servis_InsertPrijave.php";
    public static String prijavaSelect = "http://projekt001.app.fit.ba/autoskola/servis_Prijave.php";
    public static String aktivnePrijave = "http://projekt001.app.fit.ba/autoskola/servis_AktivnePrijave.php";
    public static String prijavaUpdate = "http://projekt001.app.fit.ba/autoskola/servis_PrijaveUpdate.php";
    public static String SelectKorisnikID = "http://projekt001.app.fit.ba//autoskola/servis_SelectInstruktorId.php";

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

    public static String parseDateForPrijava(String dbFormat) {
        Date temp;
        try {
            temp = new SimpleDateFormat("dd/MM/yyyy").parse(dbFormat);
            DateFormat df = new SimpleDateFormat("d.M.yyyy");

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

    public static String parseTimeTermin(String dbFormat) {
        Date temp;
        try {
            temp = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dbFormat);
            DateFormat df = new SimpleDateFormat("HH:mm");

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

    public static String parseDateTermin(String dbFormat) {
        Date temp;
        try {
            temp = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dbFormat);
            DateFormat df = new SimpleDateFormat("d.M.yyyy");

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

    public static String parseTodayForZakazivanje() {
        Date temp;
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

            // Get the date today using Calendar object.

            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            String reportDate = df.format(new Date());

            // Print what date is today!

            return reportDate;
        } catch (Exception e) {
            return "null";
        }

    }

    public static Date parseDialogFragmentCaster(String dbFormat) {
        Date temp;
        try {
            temp = new SimpleDateFormat("dd.MM.yyyy").parse(dbFormat);
            DateFormat df = new SimpleDateFormat("d.M.yyyy");

            // Get the date today using Calendar object.

            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            String reportDate = df.format(temp);

            // Print what date is today!

            return temp;

        } catch (ParseException e) {
            return null;
        }

    }

    public static String parseToFullDate(String dbFormat) {
        Date temp;
        try {
            temp = new SimpleDateFormat("dd.MM.yyyy").parse(dbFormat);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

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

    public static String round(float rating) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(rating);
    }

    public static String parseTime(String dbFormat) {
        Date temp;
        try {
            temp = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dbFormat);
            DateFormat df = new SimpleDateFormat("H:mm");

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


    public static String parseDateOnly(String dbFormat) {
        Date temp;
        try {
            temp = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dbFormat);
            DateFormat df = new SimpleDateFormat("d.M.yyyy");

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
