package com.autoskola.instruktori.map;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by haris on 1/20/15.
 */
public class MapHelper {
    private static MapHelper ourInstance = new MapHelper();


    /**
     * Gps location test
     * @return
     */
    public static final LatLng SARAJEVO = new LatLng(43.856259,18.413076);
    public static final LatLng MOSTAR = new LatLng(43.342273,17.812754);


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

    public void drawMapRoute (List<LatLng> routes,GoogleMap googleMap){

        if (googleMap != null) {
            // Add map makers
            for (int i = 0; i<routes.size(); i++){
               googleMap.addMarker(new MarkerOptions().position(routes.get(i))
                        .title("First Point"));
            }

            // Add lines on map
            Polyline line = googleMap.addPolyline(new PolylineOptions()
                    .addAll(routes)
                    .width(5)
                    .color(Color.RED));
        }
    }

}
