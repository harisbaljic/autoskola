package com.autoskola.instruktori.helpers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.autoskola.instruktori.services.model.Korisnik;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import io.fabric.sdk.android.Fabric;


public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    //// public Korisnik korisnik;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public Korisnik getLogiraniKorisnik() {
        SharedPreferences prefs = getSharedPreferences("AppSharedPereferences", Context.MODE_PRIVATE);
        String korisnik = prefs.getString("korisnik", null);
        if (korisnik != null)
            return convertFromJson(korisnik);
        else
            return null;
    }

    public Korisnik convertFromJson(String json) {
        Gson gson = new Gson();
        Korisnik korisnik = gson.fromJson(json,
                Korisnik.class);
        return korisnik;

    }
}