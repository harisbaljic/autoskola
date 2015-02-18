package com.autoskola.instruktori.services.test;

/**
 * Created by haris on 1/24/15.
 */


public class RealmFrameworkTest {
    private static RealmFrameworkTest ourInstance = new RealmFrameworkTest();

    public static RealmFrameworkTest getInstance() {
        return ourInstance;
    }

    private RealmFrameworkTest() {
    }

//    // Save object to local database
//    private void testRealmSave (Context context, GpsInfo obj){
//        GpsTask.getInstance().saveOffline(context,obj);
//    }
//
//    // Get objects from local database by VoznjaId
//    private void testRealmGet(Context context , String voznjaId){
//        GpsTask.getInstance().getAllOfflineObjects(voznjaId,context);
//    }
}
