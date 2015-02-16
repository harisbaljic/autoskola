package com.autoskola.instruktori.helpers;

import android.app.Application;

import com.autoskola.instruktori.services.model.Korisnik;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    public Korisnik korisnik;

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

}