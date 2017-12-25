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

public class ServiceGPS extends Service   implements LocationListener {

    private static LocationManager lm;
    private static Location location;
    private static String cords;
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
                Toast.LENGTH_LONG).show();

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
                Toast.makeText(applicationContext, "gps non executed return", Toast.LENGTH_SHORT).show();
            }
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, servicegps);
            } else {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, servicegps);

            }
            if (lm != null) {
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                return latitude + "," + longitude;
            }
            else return ("cordonnées non trouvées");
        }
        catch(Exception e){
            Toast.makeText(applicationContext, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            return "Error";
        }
    }

}
