package com.autoskola.instruktori.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.services.model.Korisnik;
import com.autoskola.instruktori.ui.activities.MainActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FragmentLogin extends Activity {

    private EditText mFragment_login_username;
    private EditText mFragment_login_password;
    private Button mFragment_login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        mFragment_login_username = (EditText) findViewById(R.id.fragment_login_username);
        mFragment_login_password = (EditText) findViewById(R.id.fragment_login_password);
        mFragment_login_button = (Button) findViewById(R.id.fragment_login_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
            mFragment_login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFragment_login_username.getText().toString().trim().isEmpty()  || mFragment_login_password.getText().toString().trim()
                            .isEmpty()) {
                        if (mFragment_login_username.getText().toString().trim().isEmpty()) {
                            mFragment_login_username.setError("Korisničko ime ne smije biti prazno.");
                            mFragment_login_password.setError(null);
                        }
                        if (mFragment_login_password.getText().toString().trim().isEmpty()) {
                            mFragment_login_password.setError("Lozinka ne smije biti prazna.");
                        }
                    } else {
                        mFragment_login_username.setError(null);
                        mFragment_login_password.setError(null);
                        login(mFragment_login_username.getText().toString().trim(), mFragment_login_password.getText().toString().trim());
                    }
                }
            });

    }

//    static String sha1(String input) throws NoSuchAlgorithmException {
//        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
//        byte[] result = mDigest.digest(input.getBytes());
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < result.length; i++) {
//            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
//                    .substring(1));
//        }
//        Log.d("test", sb.toString());
//        return sb.toString();
//    }

    private void login(final String korIme, String hash) {
        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<Korisnik> callback = new Callback<Korisnik>() {
            @Override
            public void success(Korisnik korisnik, Response response) {
                Log.d("Login success:", "");
                if(korisnik!=null){
                    // Save to shared prefs
                    saveKorisnikToPrefs(korisnik);
                    FragmentLogin.this.finish();
                    Intent intent = new Intent(FragmentLogin.this, MainActivity.class);
                    startActivity(intent);
                }else
                {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           new SweetAlertDialog(FragmentLogin.this, SweetAlertDialog.WARNING_TYPE)
                                   .setTitleText("Greska.")
                                   .setContentText("Ne postoji instruktor sa unesenim podacima.")
                                   .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                       @Override
                                       public void onClick(SweetAlertDialog sDialog) {
                                           sDialog.dismissWithAnimation();
                                       }
                                   })
                                   .show();
                       }
                   });
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Login fail:", error.toString());
            }
        };

        // GET request
        service.login(korIme, hash, callback);
    }

    public void saveKorisnikToPrefs(Korisnik korisnik) {
        SharedPreferences.Editor editor = getSharedPreferences("AppSharedPereferences", Context.MODE_PRIVATE).edit();
        System.out.println("Json:"+korisnik.convertToJson());
        editor.putString("korisnik", korisnik.convertToJson());
        editor.commit();
    }
}
