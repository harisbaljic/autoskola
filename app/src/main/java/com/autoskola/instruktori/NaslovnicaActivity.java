package com.autoskola.instruktori;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.autoskola.instruktori.adapters.NavDrawerAdapter;

import com.autoskola.instruktori.fragments.FragmentObavijesti;
import com.autoskola.instruktori.fragments.FragmentPrijave;
import com.autoskola.instruktori.helpers.AppController;
import com.autoskola.instruktori.helpers.MemoryManager;
import com.autoskola.instruktori.model.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by The Boss on 5.1.2015.
 */
public class NaslovnicaActivity extends Activity {

    private FrameLayout naslovnicaContainer;
    private android.app.FragmentManager supportFragmentManager;
    public static boolean loggedIn = false;
    private List<String> drawerListOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private MemoryManager memoryManager;
    public static ArrayList<NavDrawerItem> navDrawerItems;
    public static NavDrawerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naslovnica);
        memoryManager = new MemoryManager(NaslovnicaActivity.this);
        AppController.getInstance().setKorisnik(memoryManager.getKorisnik());

        navDrawerItems = new ArrayList<NavDrawerItem>();

        navDrawerItems.add(new NavDrawerItem("Naslovna",
                R.drawable.ic_action_action_home));
        navDrawerItems.add(new NavDrawerItem("GPS Menu",
                R.drawable.ic_user_profile));
        navDrawerItems.add(new NavDrawerItem("Prijave",
                R.drawable.ic_user_profile));


        naslovnicaContainer = (FrameLayout) findViewById(R.id.naslovnicacontainer);
        naslovnicaContainer.removeAllViews();
        drawerListOptions = new ArrayList<String>() {
            {
                add("Naslovna");
                add("GPS Menu");
                add("Prijave");


            }
        };

        adapter = new NavDrawerAdapter(getBaseContext(),navDrawerItems);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.activity_naslovnica_drawer_listview);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_action_navigation_menu, R.string.action_settings,
                R.string.hello_world) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                // invalidateOptionsMenu(); // creates call to
                // // onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // invalidateOptionsMenu(); // creates call to
                // // onPrepareOptionsMenu()
            }
        };

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // Set the drawer toggle as the DrawerListener


        selectItem(0);

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
    protected void onResume() {
        Log.d("Korisnik", "onResume()");
        super.onResume();
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
                    NaslovnicaActivity.this);

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
                                            NaslovnicaActivity.this,
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

private class DrawerItemClickListener implements
        ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView parent, View view, int position,
                            long id) {
        selectItem(position);
    }

}

    private void selectItem(int position) {
        naslovnicaContainer.removeAllViews();
        mDrawerLayout.closeDrawers();
       getActionBar().setTitle(navDrawerItems.get(position).getTitle());
        mDrawerList.setItemChecked(position, true);
        getActionBar().setLogo(navDrawerItems.get(position).getIcon());
        switch (position) {
            case 0:
                supportFragmentManager = getFragmentManager();
                supportFragmentManager.beginTransaction().add(R.id.naslovnicacontainer, new FragmentObavijesti(),
                        FragmentObavijesti.class.getSimpleName()).commit();
                break;



            case 1:
                supportFragmentManager = getFragmentManager();
                supportFragmentManager.beginTransaction().add(R.id.naslovnicacontainer, new FragmentPrijave(),
                        FragmentPrijave.class.getSimpleName()).commit();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        if (supportFragmentManager.getBackStackEntryCount() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    NaslovnicaActivity.this);

            // set title
            alertDialogBuilder.setTitle("Napuštate aplikaciju");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Da li ste sigurni?")
                    .setCancelable(false)
                    .setPositiveButton("Da",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    NaslovnicaActivity.this.finish();
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

    }
}