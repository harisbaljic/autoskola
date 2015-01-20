package com.autoskola.instruktori.map;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by haris on 1/20/15.
 */
public class MapHelper {
    private static MapHelper ourInstance = new MapHelper();

    public static MapHelper getInstance() {
        return ourInstance;
    }

    private MapHelper() {
    }

    public void setMapToLocation(Location location,GoogleMap map){
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Showing the current location in Google Map
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
