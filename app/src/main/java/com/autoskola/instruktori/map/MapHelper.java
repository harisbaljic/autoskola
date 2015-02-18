package com.autoskola.instruktori.map;

import android.app.Activity;
import android.graphics.Color;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.services.model.Komentar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

    public void setMapToLocation(LatLng location,GoogleMap map){

        // Showing the current location in Google Map
        map.moveCamera(CameraUpdateFactory.newLatLng(location));

        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    public void setMapToLocation(double lat , double lng,GoogleMap map){
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(lat, lng);

        // Showing the current location in Google Map
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(10));

    }

    public void drawMapRoute (final List<LatLng> routes,final GoogleMap googleMap, Activity context){

        if (googleMap != null && context!=null) {
            context.runOnUiThread(new Runnable() {
                    @Override

                    public void run() {
                        if (routes.size()>0) {
                            // Add map makers for start{
                            googleMap.addMarker(new MarkerOptions().position(routes.get(0))
                                    .title("Pocetak")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_start));

                            // Add map makers for end
                                googleMap.addMarker(new MarkerOptions().position(routes.get(routes.size()-1))
                                        .title("Kraj")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_finish));

                            // Set map to location
                            setMapToLocation(routes.get(0).latitude,routes.get(0).longitude,googleMap);
                        }

                        // Add lines on map
                        googleMap.addPolyline(new PolylineOptions()
                                .addAll(routes)
                                .width(5)
                                .color(Color.RED));

                    }
                });

        }
    }

    public void drawCommentOnMap (final Komentar komentar,final GoogleMap googleMap, final Activity context){

        if (googleMap != null) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(komentar.getLtd()),Double.valueOf(komentar.getLng())))
                                .title(komentar.getOpis())).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_comment));

                }
            });
        }
    }

    public void drawCommentsOnMap (final List<Komentar> list,final GoogleMap googleMap, final Activity context){
        if (googleMap != null) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (Komentar  komentar:list) {
                        if (komentar.getLng() != null && komentar.getLtd() != null && komentar.getLtd().toString().length()>0 && komentar.getLng().toString().length()>0) {
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(komentar.getLtd()), Double.valueOf(komentar.getLng())))
                                    .title(komentar.getOpis())).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_comment));
                        }
                    }
                }
            });

        }
    }
}
