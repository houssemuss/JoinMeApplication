package com.enseirb.joinme;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;

public class ServiceGPS extends Service   implements LocationListener {

    private static LocationManager lm;
    private static Context applicationContext;
    private static ServiceGPS servicegps;




    @Override
    public void onCreate() {

        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        applicationContext=getApplicationContext();
        servicegps=this;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }





    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(Location location) {
       Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        Toast.makeText(getBaseContext(),
                "these are your gps data location : " + latitude + " " + longitude,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public static String getCords(){

        try {
            if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "ENABLE GPS ", Toast.LENGTH_LONG).show();
            }
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,5, servicegps);
            }
            if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, servicegps);
            }
            if (lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
                lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, servicegps);
            }

            if (lm != null) {
                Location bestResult = null;
                float bestAccuracy = Float.MAX_VALUE;

                List<String> matchingProviders = lm.getAllProviders();
                for (String provider : matchingProviders) {
                    Location location = lm.getLastKnownLocation(provider);
                    if (location != null) {
                        float accuracy = location.getAccuracy();
                        if (accuracy < bestAccuracy) {
                            bestResult = location;
                        }
                    }
                }

                return bestResult.getLatitude()+","+bestResult.getLongitude();
            }

            else return ("Error");
        }
        catch(Exception e){
            return "Error";
        }
    }

}
