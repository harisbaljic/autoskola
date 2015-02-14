package com.autoskola.instruktori;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.autoskola.instruktori.gps.GpsResponseHandler;
import com.autoskola.instruktori.gps.GpsResponseTypes;
import com.autoskola.instruktori.gps.GpsTask;
import com.autoskola.instruktori.helpers.NetworkConnectivity;
import com.autoskola.instruktori.map.MapHelper;
import com.autoskola.instruktori.services.model.DataSyncState;
import com.autoskola.instruktori.services.model.GpsInfo;
import com.autoskola.instruktori.services.model.Komentar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by The Boss on 15.1.2015.
 */
public class MapaLive extends Fragment implements GpsResponseHandler,View.OnClickListener {

    private static View view;
    private GoogleMap googleMap;
    private ImageButton mBtnNewComment;
    private LatLng onLongClickPosition;

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
        mBtnNewComment = (ImageButton)view.findViewById(R.id.btnAddNewComment);
        mBtnNewComment.setOnClickListener(this);
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

            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    onLongClickPosition=latLng;
                    mBtnNewComment.performClick();
                }
            });
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

     MapHelper.getInstance().setMapToLocation(MapHelper.getInstance().SARAJEVO,googleMap);
    }

    @Override
    public void onGpsResponse(GpsResponseTypes responseType) {
      if(responseType == GpsResponseTypes.GPS_LOCATION_CHANGED){
          // Update map
         updateMap(getActivity());
      } else if (responseType == GpsResponseTypes.NEW_COMMENT){
         // updateCommentsListView();
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

    @Override
    public void onClick(View v) {

        if (v== mBtnNewComment){
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Novi komentar");
            alert.setMessage("Upisi novi komentar");
            final EditText input = new EditText(getActivity());
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    if (GpsTask.getInstance().getAktivnaPrijava(getActivity().getBaseContext())!=null) {
                        Komentar komentar = new Komentar();
                        komentar.setVoznjaId(GpsTask.getInstance().getAktivnaPrijava(getActivity()).VoznjaId);
                        komentar.setOpis(input.getText().toString());

                        // Date
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
                        f.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String utcDate = f.format(new Date());

                        komentar.setDatum(utcDate);

                        // Set gps coordinate to comment
                        if(onLongClickPosition!=null) {
                            komentar.setLtd(String.valueOf(onLongClickPosition.latitude));
                            komentar.setLng(String.valueOf(onLongClickPosition.longitude));
                            onLongClickPosition=null;
                        }else
                        {
                          // Get current gps location
                          Location location= GpsTask.getInstance().getCurrentGpsLocation();
                            if(location!=null) {
                                komentar.setLtd(String.valueOf(location.getLatitude()));
                                komentar.setLng(String.valueOf(location.getLongitude()));
                            }else
                            {
                                komentar.setLtd("");
                                komentar.setLng("");
                            }
                        }

                        addNewComment(komentar);
                    }
                    else
                        GpsTask.getInstance().showMessage("Ne mozes dodati komentar jer nema aktivne voznje...");

                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });
            alert.show();
        }
    }

    private void addNewComment(Komentar comment) {
        comment.setIsSynced(DataSyncState.SYNC_NO.ordinal());
         if(NetworkConnectivity.isConnected(getActivity())) {
             List<Komentar> list  = new ArrayList<>();
             list.add(comment);
             GpsTask.getInstance().postCommentData(list,getActivity());
         }
        // Save to local db
        GpsTask.getInstance().saveCommentOffline(getActivity(), comment);

        // Draw comment on map
        MapHelper.getInstance().drawCommentOnMap(comment, googleMap, this.getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleMap!=null){
            googleMap.clear();
            updateCommentsListView();
        }
    }

    private void updateCommentsListView (){
        if (GpsTask.getInstance().getAktivnaPrijava(getActivity().getBaseContext())!=null) {
            GpsTask.getInstance().showAllOfflineComments(GpsTask.getInstance().getAktivnaPrijava(getActivity().getBaseContext()).VoznjaId,getActivity(),googleMap);
        }
    }
}

