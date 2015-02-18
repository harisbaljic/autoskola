package com.autoskola.instruktori.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.ui.fragments.FragmentLogin;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new CountDownTimer(3000, 1500) {

            @Override
            public void onTick(long millisUntilFinished) {
                    this.cancel();
            }

            @Override
            public void onFinish() {
                    finish();
                // Check if user logged
                if(isUserLogged()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else
                {
                    Intent intent = new Intent(getApplicationContext(), FragmentLogin.class);
                    startActivity(intent);
                }
            }
        }.start();

    }

    public boolean isUserLogged(){
        SharedPreferences prefs = getSharedPreferences("AppSharedPereferences", Context.MODE_PRIVATE);
        String korisnik = prefs.getString("korisnik",null);
        if (korisnik!=null)
            return true;
        else
            return false;
    }
}
