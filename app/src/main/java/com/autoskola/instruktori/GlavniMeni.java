package com.autoskola.instruktori;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.TintEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


import com.autoskola.instruktori.fragments.FragmentDodajVoznju;
import com.autoskola.instruktori.fragments.FragmentLogin;
import com.autoskola.instruktori.fragments.FragmentObavijesti;
import com.autoskola.instruktori.fragments.FragmentPrijave;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.helpers.MemoryManager;

/**
 * Created by The Boss on 14.1.2015.
 */
public class GlavniMeni extends Activity implements View.OnClickListener   {


    private android.app.FragmentManager supportFragmentManager;
    ImageButton btnDodavanjeNoveVoznje_meni;
        ImageButton btnPretragaVoznji_meni;
        ImageButton btnListaPrijava_meni;
        ImageButton btnVoznjeMapa_meni;
        GlavniMeni glavniMeni;
        private Activity activity;
    private MemoryManager memoryManager;


    private void inicijalizacija(){
    glavniMeni = this;
    btnDodavanjeNoveVoznje_meni = (ImageButton) findViewById(R.id.btnDodavanjeNoveVoznje_meni);
    btnPretragaVoznji_meni = (ImageButton) findViewById(R.id.btnPretragaVoznji_meni);
    btnListaPrijava_meni = (ImageButton) findViewById(R.id.btnListaPrijava_meni);
    btnVoznjeMapa_meni = (ImageButton) findViewById(R.id.btnVoznjeMapa_meni);

    btnDodavanjeNoveVoznje_meni.setOnClickListener(this);
    btnPretragaVoznji_meni.setOnClickListener(this);
        btnListaPrijava_meni.setOnClickListener(this);
    btnVoznjeMapa_meni.setOnClickListener(this);

        activity = this;
}

    @Override
    public void onClick(View v) {
        Intent i;

        if(v == btnDodavanjeNoveVoznje_meni)
        {
            setContentView(R.layout.activity_naslovnica);
            getFragmentManager().beginTransaction().replace(R.id.naslovnicacontainer, new FragmentDodajVoznju(),
                    FragmentDodajVoznju.class.getSimpleName()).addToBackStack(FragmentDodajVoznju.class.getSimpleName()).commit();

        }

       else if(v == btnVoznjeMapa_meni)
        {
            i = new Intent(activity, MapaLive.class);
            startActivity(i);

          /*  supportFragmentManager = getFragmentManager();
            supportFragmentManager.beginTransaction().add(R.id.LinearLayout1, new FragmentMapa(),
                    FragmentMapa.class.getSimpleName()).commit(); */
        }


        else if(v == btnListaPrijava_meni)
        {
            setContentView(R.layout.activity_naslovnica);
            supportFragmentManager = getFragmentManager();
            supportFragmentManager.beginTransaction().replace(R.id.naslovnicacontainer, new FragmentPrijave(),
                    FragmentPrijave.class.getSimpleName()).addToBackStack(FragmentPrijave.class.getSimpleName()).commit();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.glavni_meni);
        inicijalizacija();

        memoryManager = new MemoryManager(GlavniMeni.this);
        AppController.getInstance().setKorisnik(memoryManager.getKorisnik());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.naslovnica, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    GlavniMeni.this);

            // set title
            alertDialogBuilder.setTitle("Odjava iz aplikacije");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Da li ste sigurni?")
                    .setCancelable(false)
                    .setPositiveButton("Da",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    memoryManager.logOutUser();
                                    finish();
                                    startActivity(new Intent(
                                            GlavniMeni.this,
                                            SplashScreen.class));
                                }
                            })
                    .setNegativeButton("Ne",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
        // if (id == R.id.action_user_profile) {
        // // naslovnicaContainer.removeAllViews();
        //
        // // Fragment fragment = new FragmentLogin();
        // // getSupportFragmentManager()
        // // .beginTransaction()
        // // .replace(R.id.maincontainer, new FragmentLogin(),
        // // FragmentLogin.class.getSimpleName())
        // // .addToBackStack(FragmentLogin.class.getSimpleName())
        // // .commit();
        // Log.d("NaslovnicaActivity", new Gson().toJson(AppController
        // .getInstance().getKorisnik()));
        // }
       // if (mDrawerToggle.onOptionsItemSelected(item)) {
       //     return true;
     //   }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        setContentView(R.layout.glavni_meni);
        inicijalizacija();
    }

}
