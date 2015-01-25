package com.autoskola.instruktori;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.autoskola.instruktori.gps.GpsResponseHandler;
import com.autoskola.instruktori.gps.GpsResponseTypes;
import com.autoskola.instruktori.gps.GpsTask;
import com.autoskola.instruktori.map.MapHelper;
import com.autoskola.instruktori.services.model.GpsInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by The Boss on 15.1.2015.
 */
public class MapaLive extends Activity implements GpsResponseHandler {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__mapa);
        GpsTask.getInstance().communicatorInterfaceMap=this;

        try {
            // Loading map
            initilizeMap();
            updateMap(this);

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

            //googleMap.setMyLocationEnabled(true);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

     MapHelper.getInstance().setMapToLocation(GpsTask.getInstance().getCurrentGpsLocation(),googleMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onGpsResponse(GpsResponseTypes responseType) {
      if(responseType == GpsResponseTypes.GPS_LOCATION_CHANGED){
          // Update map
         updateMap(this);
      }
   }

  private void updateMap (final Activity activity){
      // Check for active prijava
      if (GpsTask.getInstance().getAktivnaPrijava(getBaseContext())!=null) {
          new Thread(new Runnable() {
              public void run() {

                  Realm realm = Realm.getInstance(getBaseContext());
                  RealmResults<GpsInfo> gpsList = realm.where(GpsInfo.class)
                          .equalTo("voznjaId", GpsTask.getInstance().getAktivnaPrijava(getBaseContext()).VoznjaId)
                          .findAll();


                  List<LatLng> locations = new ArrayList<LatLng>();
                  for(int i=0;i<gpsList.size();i++){
                      locations.add(new LatLng(Double.valueOf(gpsList.get(i).getLatitude()),Double.valueOf(gpsList.get(i).getLongitude())));

                  }

                  MapHelper.getInstance().drawMapRoute(locations,googleMap,activity);
                  //new LatLng(43.856259,18.413076);

              }
          }).start();
      }

    }
}
