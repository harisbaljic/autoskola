package com.autoskola.instruktori.gps;
import android.location.LocationManager;
import android.content.Context;
import android.location.Location;
import android.widget.Toast;

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
}
