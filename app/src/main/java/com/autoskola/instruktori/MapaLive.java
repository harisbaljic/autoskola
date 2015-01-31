package com.autoskola.instruktori;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class MapaLive extends Fragment implements GpsResponseHandler {

    private static View view;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment__mapa, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GpsTask.getInstance().communicatorInterfaceMap=this;
        try {
            // Loading map
            initilizeMap();
            updateMap(getActivity());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

            //googleMap.setMyLocationEnabled(true);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

     MapHelper.getInstance().setMapToLocation(GpsTask.getInstance().getCurrentGpsLocation(),googleMap);
    }

    @Override
    public void onGpsResponse(GpsResponseTypes responseType) {
      if(responseType == GpsResponseTypes.GPS_LOCATION_CHANGED){
          // Update map
         updateMap(getActivity());
      }
   }

  private void updateMap (final Activity activity){
      // Check for active prijava
      if (GpsTask.getInstance().getAktivnaPrijava(activity.getBaseContext())!=null) {
          new Thread(new Runnable() {
              public void run() {

                  Realm realm = Realm.getInstance(activity.getBaseContext());
                  RealmResults<GpsInfo> gpsList = realm.where(GpsInfo.class)
                          .equalTo("voznjaId", GpsTask.getInstance().getAktivnaPrijava(activity.getBaseContext()).VoznjaId)
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
