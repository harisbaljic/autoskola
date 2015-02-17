package com.autoskola.instruktori.ui;


import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.autoskola.instruktori.MapaLive;
import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.MenuDrawerAdapter;
import com.autoskola.instruktori.fragments.FragmentDodajVoznju;
import com.autoskola.instruktori.fragments.FragmentObavijesti;
import com.autoskola.instruktori.fragments.FragmentPrijave;
import com.autoskola.instruktori.fragments.FragmentSettings;
import com.autoskola.instruktori.fragments.FragmentSyncStatus;
import com.autoskola.instruktori.fragments.FragmentZavrseneVoznje;
import com.autoskola.instruktori.gps.GpsResponseHandler;
import com.autoskola.instruktori.gps.GpsResponseTypes;
import com.autoskola.instruktori.gps.GpsTask;
import com.autoskola.instruktori.helpers.NetworkConnectivity;

import java.util.Arrays;


public class MainActivity2 extends FragmentActivity  implements GpsResponseHandler {

    String[] mDrawerTitles = {
            "Vijesti",
            "Dodaj voznju",
            "Prijave",
            "Mapa",
            "Zavrsene voznje",
            "Settings"
    };
    private MenuDrawerAdapter mDrawerAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private FrameLayout mContentFrame;
    private ActionBarDrawerToggle mDrawerToggle;

    private  FragmentSyncStatus fragmentSync = new FragmentSyncStatus();
    private  FragmentObavijesti fragmentObavijesti = new FragmentObavijesti();
    private  FragmentDodajVoznju fragmentDodajVoznju = new FragmentDodajVoznju();
    private  FragmentPrijave fragmentPrijave  = new FragmentPrijave();
    private  MapaLive fragmentMapaLive = new MapaLive();
    private  FragmentZavrseneVoznje  fragmentZavrseneVoznje  = new FragmentZavrseneVoznje();
    private FragmentSettings fragmentSettings = new FragmentSettings();



    private void initLayoutObjects (){
        mContentFrame = (FrameLayout)findViewById(R.id.content_frame_fragment);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
    }

    private void initDrawerAdapter (){

        // Set the adapter for the list view
        mDrawerAdapter = new MenuDrawerAdapter(this, Arrays.asList(mDrawerTitles));
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mDrawerLayout.closeDrawers();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        setFragment(position);
                    }
                }, 200);

            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_title_closed,  /* "open drawer" description */
                R.string.drawer_title_closed  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        // Init gps location manager
        GpsTask.getInstance().initGpsManager(this, LocationManager.GPS_PROVIDER);
       // GpsTask.getInstance().initGpsManager(this, LocationManager.NETWORK_PROVIDER);
        // Get layout refs
        initLayoutObjects();

        // Init drawer adapter and set listener
        initDrawerAdapter();

        // Action bar icon and toogle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // Set first fragment
        setFragment(0);

        // Sync data
        GpsTask.getInstance().syncVoznjeStatus(this);
        GpsTask.getInstance().syncComments(this);
        GpsTask.getInstance().syncGpsInfo(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // If sync clicked
        if ( item.getItemId() ==  R.id.action_sync){
            if (NetworkConnectivity.isConnected(this)) {
                GpsTask.getInstance().syncVoznjeStatus(this);
                GpsTask.getInstance().syncComments(this);
                GpsTask.getInstance().syncGpsInfo(this);
            }
            else
            {
                GpsTask.getInstance().showMessage("No internet connection");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFragment(int position){

        Fragment fragment = null;
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager(); // For AppCompat use getSupportFragmentManager
        switch(position) {
            default:
            case 0:
                // Vijesti
                fragment = fragmentObavijesti;
                getActionBar().setTitle("Vijesti");
                break;
            case 1:
                // Dodaj voznju
                fragment =  fragmentDodajVoznju;//new FragmentDodajVoznju();
                getActionBar().setTitle("Dodaj voznju");
                break;

            case 2:
                // Prijave
                fragment = fragmentPrijave;
                getActionBar().setTitle("Prijave");
                break;

            case 3:
                // Mapa
               fragment = fragmentMapaLive;
                getActionBar().setTitle("Mapa");
                break;

            case 4:
                // Zavrsene voznje
                fragment = fragmentZavrseneVoznje;
                getActionBar().setTitle("Zavrsene voznje");
                break;
            case 5:
                // Settings
                fragment = fragmentSettings;
                getActionBar().setTitle("Settings");
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame_fragment, fragment)
                .commit();

    }
    @Override
    public void onGpsResponse(GpsResponseTypes responseType) {
        GpsTask.getInstance().showMessage("Type:"+responseType);
        if (responseType == GpsResponseTypes.WI_FI_CONNECTION || responseType == GpsResponseTypes.MOBILE_CONNECTION ){
            GpsTask.getInstance().syncVoznjeStatus(this);
            GpsTask.getInstance().syncComments(this);
            GpsTask.getInstance().syncGpsInfo(this);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity2, menu);
        return true;
    }
}
