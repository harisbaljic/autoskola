package com.autoskola.instruktori.gps;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.autoskola.instruktori.helpers.NetworkConnectivity;
import com.autoskola.instruktori.map.MapHelper;
import com.autoskola.instruktori.services.GpsWebService;
import com.autoskola.instruktori.services.PrijavaWebService;
import com.autoskola.instruktori.services.model.CommentSyncState;
import com.autoskola.instruktori.services.model.GpsInfo;
import com.autoskola.instruktori.services.model.Komentar;
import com.autoskola.instruktori.services.model.Prijava;
import com.autoskola.instruktori.services.model.Voznja;
import com.autoskola.instruktori.services.model.VoznjaSimple;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 1/20/15.
 */
public class GpsTask {
    private static GpsTask ourInstance = new GpsTask();

    // Location manager
    private LocationManager mLocationManager;
    private LocationListener locationListener;

    /**
     * NETWORK_PROVIDER - Name of the network location provider.
     * This provider determines location based on
     * availability of cell tower and WiFi access points. Results are retrieved
     * by means of a network lookup.
     *
     * GPS_PROVIDER - Name of the GPS location provider.
     *
     * This provider determines location using
     * satellites. Depending on conditions, this provider may take a while to return
     * a location fix.
     */
    private String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

    // GPS - Network state listener
    public GpsResponseHandler communicatorInterface;
    public GpsResponseHandler communicatorInterfaceMap;

    /**
     * This is singleton instance for this class
     */
    public static GpsTask getInstance() {

        return ourInstance;
    }


    /**
     * This function is used to init GPS location manager and set GPS provider
     */
    public void initGpsManager (Context context, String provider){
        // Set network/gps state listener
        this.communicatorInterface = (GpsResponseHandler)context;

        // Set provider
        this.LOCATION_PROVIDER = provider;

        // Init location manager
        if (GpsTask.getInstance().mLocationManager == null) {
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        // Init location listener
        locationListener = new GpsLocationListener(context);
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, this.locationListener);
    }

    private final class GpsLocationListener implements LocationListener {

        private Context context;
        public  GpsLocationListener (Context context){
            this.context = context;
        }

        @Override
        public void onLocationChanged(Location locFromGps) {
            // called when the listener is notified with a location update from the GPS
            showMessage("Lat:"+locFromGps.getLatitude() + "Lng:"+locFromGps.getLongitude());
            if (getAktivnaPrijava(context)!=null){

                GpsInfo info = new GpsInfo();
                info.setVoznjaId(getAktivnaPrijava(context).VoznjaId);
                info.setLatitude(String.valueOf(locFromGps.getLatitude()));
                info.setLongitude(String.valueOf(locFromGps.getLongitude()));

                // Check internet connection
                if(NetworkConnectivity.isConnected(context)){
                    // Save online
                    ArrayList<GpsInfo>list = new ArrayList<>();
                    info.setIsSynced(CommentSyncState.COMMENT_SYNC_IN_PROGRESS.ordinal());
                    list.add(info);
                    postGpsData(list,context);
                }
                else
                    info.setIsSynced(CommentSyncState.COMMENT_SYNC_NO.ordinal());

                // Save offline
                GpsTask.getInstance().saveGpsInfoOffline(context, info);

                // Notify map fragment for change
                if (communicatorInterfaceMap!=null) {
                    communicatorInterfaceMap.onGpsResponse(GpsResponseTypes.GPS_LOCATION_CHANGED);
                }
            }
            else
                showMessage("Lokacija je promjenjena, ali nema aktivnih prijava");
        }

        @Override
        public void onProviderDisabled(String provider) {
            // called when the GPS provider is turned off (user turning off the GPS on the phone)
            showMessage("GPS Provider is turned off");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // called when the GPS provider is turned on (user turning on the GPS on the phone)
            showMessage("GPS Provider is turned on");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // called when the status of the GPS provider changes
            showMessage("GPS status changed:"+status);
        }
    }

    private GpsTask() {
    }

    public Location getCurrentGpsLocation (){
        // Get current location if available
        Location location = mLocationManager.getLastKnownLocation(LOCATION_PROVIDER);
        return location;
    }

    public void showMessage(String message){
        Toast.makeText((Context)this.communicatorInterface,message,Toast.LENGTH_SHORT).show();
    }

    // Save aktivna prijava to preference
    public void startGPSTask(Prijava prijava,Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("AppSharedPereferences",Context.MODE_PRIVATE ).edit();
        editor.putString("AktivnaPrijava", prijava.convertToJson());
        editor.commit();

        // Save voznja offline
        saveVoznjaOffline(prijava,context);
    }

    // Remove aktivna prijava from preference
    public void stopGpsTask(Context context){
        // Update status
        updateVoznjaOfflineStatus(getAktivnaPrijava(context),context);

        // Remove from preference
        SharedPreferences.Editor editor = context.getSharedPreferences("AppSharedPereferences",Context.MODE_PRIVATE ).edit();
        editor.putString("AktivnaPrijava",null);
        editor.commit();
    }


    // Get aktivna prijava from preferene
    public Prijava getAktivnaPrijava (Context context){
        SharedPreferences prefs = context.getSharedPreferences("AppSharedPereferences",Context.MODE_PRIVATE);
        String prijava_json = prefs.getString("AktivnaPrijava", null);
        return new Prijava().convertFromJson(prijava_json);
    }

    //////////////////////////////////////////////////////////////
    // FUNCTIONS FOR SAVE COMMENTS
    //////////////////////////////////////////////////////////////

    public void postCommentData(List<Komentar>listKomentara, final Context context){

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        GpsWebService service = restAdapter.create(GpsWebService.class);

        // Callback
        Callback<List<Komentar>> callback = new Callback<List<Komentar>>() {
            @Override
            public void success(List<Komentar> aBoolean, Response response) {
                System.out.println("POST Comments - success");
                // Remove comments locally
                Realm realm = Realm.getInstance(context);
                RealmResults<Komentar> commentList = realm.where(Komentar.class)
                       .equalTo("isSynced",CommentSyncState.COMMENT_SYNC_IN_PROGRESS.ordinal()).findAll();

                realm.beginTransaction();
                for (Komentar komentar : commentList){
                    komentar.setIsSynced(CommentSyncState.COMMENT_SYNC_YES.ordinal());
                }
                realm.commitTransaction();
                GpsTask.getInstance().showMessage("Comment synced successfuly");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("POST Comments - fail:"+error);
                //GpsTask.getInstance().communicatorInterface.onGpsResponse(GpsResponseTypes.GPS_SYNC_FAIL);
            }
        };

        // POST request
        service.postGpsKomentar(listKomentara, callback);
    }

    public void syncComments(final Context context){
        new Thread(new Runnable() {
            public void run() {
                Realm realm = Realm.getInstance(context);
                RealmResults<Komentar> commentList = realm.where(Komentar.class)
                        .equalTo("isSynced",CommentSyncState.COMMENT_SYNC_NO.ordinal())
                        .findAll();

                realm.beginTransaction();
                List<Komentar>finalCommentList = new ArrayList<Komentar>();
                for (int i=0;i<commentList.size();i++){
                    Komentar komentar  = new Komentar();
                    komentar.setLng("");
                    komentar.setLtd("");
                    komentar.setDatum(commentList.get(i).getDatum());
                    komentar.setOpis(commentList.get(i).getOpis());
                    komentar.setVoznjaId(commentList.get(i).getVoznjaId());
                    finalCommentList.add(komentar);

                    // Update local comment object
                    commentList.get(i).setIsSynced(CommentSyncState.COMMENT_SYNC_IN_PROGRESS.ordinal());
                }
                realm.commitTransaction();
                postCommentData(finalCommentList, context);
            }
        }).start();
    }

    public void showAllOfflineComments (final String voznjaId,final Activity context,final GoogleMap map){
        new Thread(new Runnable() {
            public void run() {

                Realm realm = Realm.getInstance(context);
                RealmResults<Komentar> commentsList = realm.where(Komentar.class)
                        .equalTo("voznjaId",voznjaId)
                        .findAll();
                List <Komentar> list = new ArrayList<Komentar>(commentsList);

                // Draw comments
                MapHelper.getInstance().drawCommentsOnMap(list,map,context);

            }
        }).start();
    }


    public void saveCommentOffline(Context context,Komentar comment){

        // Get realm instance
        Realm realm = Realm.getInstance(context);

        // Begin db transactions
        realm.beginTransaction();
        Komentar realmObject = realm.createObject(Komentar.class);
        realmObject.setVoznjaId(comment.getVoznjaId());
        realmObject.setDatum(comment.getDatum());
        realmObject.setLng(comment.getLng());
        realmObject.setLtd(comment.getLtd());
        realmObject.setOpis(comment.getOpis());
        realmObject.setIsSynced(comment.getIsSynced());

        // Save to db
        realm.commitTransaction();

        // Send notification to map fragment
        communicatorInterfaceMap.onGpsResponse(GpsResponseTypes.NEW_COMMENT);
    }

    //////////////////////////////////////////////////////////////
    // FUNCTIONS FOR SAVE GPS DATA
    //////////////////////////////////////////////////////////////

    public void postGpsData (List<GpsInfo> gpsData, final Context context){

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        GpsWebService service = restAdapter.create(GpsWebService.class);

        // Callback
        Callback<List<GpsInfo>> callback = new Callback<List<GpsInfo>>() {
            @Override
            public void success(List<GpsInfo> aBoolean, Response response) {
                System.out.println("POST GpsInfo - success");
                // Remove comments locally
                Realm realm = Realm.getInstance(context);
                RealmResults<GpsInfo> commentList = realm.where(GpsInfo.class)
                        .equalTo("isSynced",CommentSyncState.COMMENT_SYNC_IN_PROGRESS.ordinal()).findAll();

                realm.beginTransaction();
                for (GpsInfo komentar : commentList)
                    komentar.setIsSynced(CommentSyncState.COMMENT_SYNC_YES.ordinal());

                realm.commitTransaction();

                GpsTask.getInstance().communicatorInterface.onGpsResponse(GpsResponseTypes.GPS_SYNC_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("POST GpsInfo - fail:"+error);
                GpsTask.getInstance().communicatorInterface.onGpsResponse(GpsResponseTypes.GPS_SYNC_FAIL);
            }
        };

        // POST request
        service.postGpsInfo(gpsData, callback);
    }

    // Sync local db to server
    public void syncGpsInfo (final Context context){

        new Thread(new Runnable() {
            public void run() {
                Realm realm = Realm.getInstance(context);
                RealmResults<GpsInfo> gpsList = realm.where(GpsInfo.class).
                        equalTo("isSynced", CommentSyncState.COMMENT_SYNC_NO.ordinal()).
                        findAll();
                realm.beginTransaction();

                // Set progress
                ArrayList<GpsInfo> list  = new ArrayList<>();
                for (GpsInfo info : gpsList) {
                    GpsInfo obj = new GpsInfo();
                    obj.setLatitude(info.getLatitude());
                    obj.setLongitude(info.getLongitude());
                    obj.setVoznjaId(info.getVoznjaId());
                    list.add(obj);
                    System.out.println("Lat: "+info.getLatitude() + "Lng:"+info.getLongitude() + "Status:"+info.getIsSynced());
                    info.setIsSynced(CommentSyncState.COMMENT_SYNC_IN_PROGRESS.ordinal());
                }

                // Commit update
                realm.commitTransaction();

                // Post
                postGpsData(list, context);
            }
        }).start();
    }

    // Save gps info object  to local db
    public void saveGpsInfoOffline (Context context,GpsInfo gpsObject){
        // Get realm instance
        Realm realm = Realm.getInstance(context);

        // Begin db transactions
        realm.beginTransaction();
        GpsInfo realmObject = realm.createObject(GpsInfo.class);
        realmObject.setDetaljiVoznjeId("");
        realmObject.setVoznjaId(gpsObject.getVoznjaId());
        realmObject.setLatitude(gpsObject.getLatitude());
        realmObject.setLongitude(gpsObject.getLongitude());
        realmObject.setIsSynced(gpsObject.getIsSynced());

        // Save to db
        realm.commitTransaction();
    }

    //////////////////////////////////////////////////////////////
    // FUNCTIONS FOR SAVE VOZNJA
    //////////////////////////////////////////////////////////////

    private  void updateVoznjaOfflineStatus (final Prijava prijava,final  Context context){

        new Thread(new Runnable() {
            public void run() {
                Realm realm = Realm.getInstance(context);
                realm.beginTransaction();
                Voznja voznja = realm.where(Voznja.class)
                        .equalTo("voznjaId",prijava.VoznjaId)
                        .findFirst();

                if (voznja !=null){
                    voznja.setStatus(1);
                    realm.commitTransaction();
                }
            }
        }).start();

    }

    public void saveVoznjaOffline (Prijava prijava, Context context){

        // Get realm instance
        Realm realm = Realm.getInstance(context);
        Voznja voznja = realm.where(Voznja.class)
                .equalTo("voznjaId",prijava.VoznjaId)
                .findFirst();

        // Prevent duplication
        if (voznja == null){
            // Begin db transactions
            realm.beginTransaction();

            // Create new object
            Voznja realmObject = realm.createObject(Voznja.class);
            realmObject.setVoznjaId(prijava.VoznjaId);
            realmObject.setStatus(0);
            realmObject.setDate(Calendar.getInstance().getTime().toString());
            realmObject.setIme(prijava.Ime);
            realmObject.setPrezime(prijava.Prezime);

            // Save to db
            realm.commitTransaction();
        }

    }

    private void postVoznja ( final VoznjaSimple voznja,final Context context){

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        PrijavaWebService service = restAdapter.create(PrijavaWebService.class);

        // Callback
        Callback<VoznjaSimple> callback = new Callback<VoznjaSimple>() {
            @Override
            public void success(VoznjaSimple prijave, Response response) {
                Log.d("Update voznje - success:", "");

                // Update sync state
                Realm realm = Realm.getInstance(context);
                realm.beginTransaction();
                Voznja object = realm.where(Voznja.class)
                        .equalTo("voznjaId",voznja.getVoznjaId())
                        .findFirst();

               object.setIsSynced(CommentSyncState.COMMENT_SYNC_YES.ordinal());
               realm.commitTransaction();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Update voznje - fail:",error.toString() + error.getResponse());
            }
        };

        // GET request
        service.updateVoznje(voznja, callback);
    }

    public void syncVoznjeStatus (final Context context){

        new Thread(new Runnable() {
            public void run() {
                Realm realm = Realm.getInstance(context);
                RealmResults<Voznja> voznjeList = realm.where(Voznja.class)
                        .equalTo("status", 1)
                        .notEqualTo("isSynced", CommentSyncState.COMMENT_SYNC_NO.ordinal())
                        .findAll();

                realm.beginTransaction();
                for (Voznja object : voznjeList) {
                    VoznjaSimple simple = new VoznjaSimple();
                    simple.setVoznjaId(object.getVoznjaId());
                    simple.setStatus(object.getStatus());
                    object.setIsSynced(CommentSyncState.COMMENT_SYNC_IN_PROGRESS.ordinal());
                    postVoznja(simple, context);
                }
                realm.commitTransaction();


            }
        }).start();

    }

}
