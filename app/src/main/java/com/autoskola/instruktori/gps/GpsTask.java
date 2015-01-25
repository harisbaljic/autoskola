package com.autoskola.instruktori.gps;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.autoskola.instruktori.services.GpsWebService;
import com.autoskola.instruktori.services.model.GpsInfo;
import com.autoskola.instruktori.services.model.Prijava;

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

    // Save gps info object  to local db
    public void saveOffline (Context context,GpsInfo gpsObject){
        // Get realm instance
        Realm realm = Realm.getInstance(context);

        // Begin db transactions
        realm.beginTransaction();
        GpsInfo realmObject = realm.createObject(GpsInfo.class);
        realmObject.setDetaljiVoznjeId(gpsObject.getDetaljiVoznjeId());
        realmObject.setVoznjaId(gpsObject.getVoznjaId());
        realmObject.setLatitude(gpsObject.getLatitude());
        realmObject.setLongitude(gpsObject.getLongitude());

        // Save to db
        realm.commitTransaction();
    }

    // Get all gps info objects by voznjaId
    public void   getAllOfflineObjects(final String voznjaId,final Context context){

        new Thread(new Runnable() {
            public void run() {

                Realm realm = Realm.getInstance(context);
                RealmResults<GpsInfo> gpsList = realm.where(GpsInfo.class)
                        .equalTo("voznjaId",voznjaId)
                        .findAll();

                for (int i = 0; i <gpsList.size() ; i++) {
                    System.out.println("Offline objects:"+gpsList.get(i).getLatitude());
                }
            }
        }).start();
    }

    // Sync local db to server
    public void syncGpsData (final Context context){

        new Thread(new Runnable() {
            public void run() {
                Realm realm = Realm.getInstance(context);
                RealmResults<GpsInfo> gpsList = realm.where(GpsInfo.class).findAll();
                postGpsData(gpsList);
            }
        }).start();
    }

    public void postGpsData (List<GpsInfo> gpsData){

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

    // Save aktivna prijava to preference
    public void startGPSTask(Prijava prijava,Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("AppSharedPereferences",Context.MODE_PRIVATE ).edit();
        editor.putString("AktivnaPrijava", prijava.convertToJson());
        editor.commit();
    }

    // Remove aktivna prijava from preference
    public void stopGpsTask(Context context){
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
}
