package com.autoskola.instruktori.helpers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.autoskola.instruktori.gps.GpsResponseTypes;
import com.autoskola.instruktori.gps.GpsTask;

/**
 * Created by haris on 1/21/15.
 */
public class NetworkStateReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if(GpsTask.getInstance().communicatorInterface!=null){
            if(NetworkConnectivity.isConnected(context)){

                if(NetworkConnectivity.isConnectedWifi(context)){
                    System.out.println("Connected to Wi-Fi");
                    GpsTask.getInstance().communicatorInterface.onGpsResponse(GpsResponseTypes.WI_FI_CONNECTION);
                }else if(NetworkConnectivity.isConnectedMobile(context)){
                    System.out.println("Connected to mobile network");
                    GpsTask.getInstance().communicatorInterface.onGpsResponse(GpsResponseTypes.MOBILE_CONNECTION);
                }
            }
            else
            {
                System.out.println("Internet Disconnected state");
                GpsTask.getInstance().communicatorInterface.onGpsResponse(GpsResponseTypes.NO_INTERNET_CONNECTION);
            }
        }
    }

}
