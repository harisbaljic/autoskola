package com.autoskola.instruktori;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.autoskola.instruktori.fragments.FragmentLogin;
import com.autoskola.instruktori.fragments.FragmentObavijesti;



public class MainActivity extends Activity
{

    private RelativeLayout mainContainer;
    private FragmentManager supportFragmentManager;
    public static boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Korisnik","OnCreate() ");

        mainContainer = (RelativeLayout) findViewById(R.id.maincontainer);
        mainContainer.removeAllViews();
        supportFragmentManager = getFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.maincontainer,new FragmentObavijesti(),
                FragmentObavijesti.class.getSimpleName()).commit();
    }

    @Override
    protected void onResume() {
        Log.d("Korisnik", "onResume()");
        super.onResume();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("rrer", loggedIn + "");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_login)
        {
            /*mainContainer.removeAllViews();*/
            Fragment fragment = new FragmentLogin();
            getFragmentManager().beginTransaction().replace(R.id.maincontainer, new FragmentLogin(),
                    FragmentLogin.class.getSimpleName()).addToBackStack(FragmentLogin.class.getSimpleName()).commit();
            item.setVisible(false);

        }



        return super.onOptionsItemSelected(item);
    }

}
