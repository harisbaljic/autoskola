package com.autoskola.instruktori.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autoskola.instruktori.GlavniMeni;
import com.autoskola.instruktori.NaslovnicaActivity;
import com.autoskola.instruktori.R;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.helpers.Helper;
import com.autoskola.instruktori.helpers.MemoryManager;
import com.autoskola.instruktori.model.Korisnik;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class FragmentLogin extends Fragment {

    private EditText mFragment_login_username;
    private EditText mFragment_login_password;
    private Button mFragment_login_button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        mFragment_login_username = (EditText) view.findViewById(R.id.fragment_login_username);
        mFragment_login_password = (EditText) view.findViewById(R.id.fragment_login_password);
        mFragment_login_button = (Button) view.findViewById(R.id.fragment_login_button);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");

        mFragment_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragment_login_username.getText().toString().trim()
                        .isEmpty()
                        || mFragment_login_password.getText().toString().trim()
                        .isEmpty()) {
                    if (mFragment_login_username.getText().toString().trim()
                            .isEmpty()) {
                        mFragment_login_username
                                .setError("Korisničko ime ne smije biti prazno.");
                        mFragment_login_password.setError(null);
                    }
                    if (mFragment_login_password.getText().toString().trim()
                            .isEmpty()) {
                        mFragment_login_password
                                .setError("Lozinka ne smije biti prazna.");
                        // mFragment_login_password.setError(null);
                    }
                } else {
                    mFragment_login_username.setError(null);
                    mFragment_login_password.setError(null);
                    // progressDialog.show();
                    RequestParams params = new RequestParams();
                    params.put("korIme", mFragment_login_username.getText()
                            .toString());
                    //  params.put("hash", sha1(mFragment_login_password
                    //    .getText().toString()));
                    params.put("hash",mFragment_login_password.getText().toString());

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post(Helper.login, params,
                            new JsonHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode,
                                                      Header[] headers, JSONObject response) {
                                    // TODO Auto-generated method stub
                                    super.onSuccess(statusCode, headers,
                                            response);
                                    progressDialog.hide();

                                    try {
                                        Korisnik k = new Gson().fromJson(
                                                response.toString(),
                                                Korisnik.class);
                                        k.LozinkaHash = sha1(mFragment_login_password
                                                .getText().toString());
                                        AppController.getInstance()
                                                .setKorisnik(k);

                                        MemoryManager memoryManager = new MemoryManager(
                                                getActivity());
                                        memoryManager.logInUser(new Gson()
                                                .toJson(k));
                                       getActivity().finish();
                                        startActivity(new Intent(getActivity(),
                                               GlavniMeni.class));
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

                                }

                                @Override
                                public void onFailure(int statusCode,
                                                      Header[] headers,
                                                      String responseString,
                                                      Throwable throwable) {
                                    // TODO Auto-generated method stub
                                    super.onFailure(statusCode, headers,
                                            responseString, throwable);
                                    progressDialog.hide();
                                    Log.d("fail", responseString + " "
                                            + throwable.getMessage());
                                    Toast.makeText(
                                            getActivity(),
                                            "Pogrešno korisničko ime i/ili lozinka.",
                                            Toast.LENGTH_LONG).show();
                                    mFragment_login_password.setText("");
                                    mFragment_login_username.setText("");
                                    mFragment_login_username.requestFocus();
                                }

                            });

                }
            }


        });}

    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        Log.d("test", sb.toString());
        return sb.toString();
    }
}
