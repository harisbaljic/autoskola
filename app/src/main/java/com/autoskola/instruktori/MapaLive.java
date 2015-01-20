package com.autoskola.instruktori;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.autoskola.instruktori.gps.GpsTask;
import com.autoskola.instruktori.map.MapHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;

/**
 * Created by The Boss on 15.1.2015.
 */
public class MapaLive extends Activity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__mapa);

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        // Show current location on map
        MapHelper.getInstance().setMapToLocation(GpsTask.getInstance().getCurrentGpsLocation(),googleMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
}
