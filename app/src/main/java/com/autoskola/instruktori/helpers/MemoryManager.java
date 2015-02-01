package com.autoskola.instruktori.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.autoskola.instruktori.services.model.Korisnik;
import com.google.gson.Gson;

public class MemoryManager {
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AutoskolaPrefs";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_JSON = "json";

    public MemoryManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void logInUser(String json) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_JSON, json);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public Korisnik getKorisnik() {
        if (isLoggedIn())
            return new Gson().fromJson(pref.getString(KEY_JSON, ""),
                    Korisnik.class);
        return null;
    }

    public void logOutUser() {
        editor.clear();
        editor.commit();
    }
}
