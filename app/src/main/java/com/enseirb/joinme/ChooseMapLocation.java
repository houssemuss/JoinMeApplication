package com.enseirb.joinme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hcherif.enseirb.com.joinmeapplication.R;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.StringTokenizer;

public class ChooseMapLocation extends AppCompatActivity implements OnMapReadyCallback {
    private   SupportMapFragment mMap;
    private final StringBuilder gcords=new StringBuilder();
    private OnMapReadyCallback current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_map_location);
        try {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_location));
            mMap.getMapAsync(this);
            onMapReady(mMap.getMap());
            current=this;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "map non active", Toast.LENGTH_SHORT).show();
            return;
        }


        map.setMyLocationEnabled(true);
     // final   Location mLocation = map.getMyLocation();
        String cords=ServiceGPS.getCords();
        StringTokenizer st=new StringTokenizer(cords,",");
        double latitude=0;
        double longitude=0;
        try {
             latitude = Double.parseDouble(st.nextToken());
             longitude = Double.parseDouble(st.nextToken());
        }
        catch(Exception e){
            latitude=44.807460;
            longitude=-0.605274;
        }

        LatLng mLocation=new LatLng(latitude,longitude);
        map.addMarker(new MarkerOptions().position(mLocation).draggable(true));
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDrag(Marker marker) {

/*                onMapReady(mMap.getMap());
                LatLng newLocation = marker.getPosition();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 15.0f));*/
              //  onMapReady(mMap.getMap());
                mMap.getMapAsync(current);
                onMapReady(mMap.getMap());
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng newLocation = marker.getPosition();
             //   mLocation.setLatitude(newLocation.latitude);
              //  mLocation.setLongitude(newLocation.longitude);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 13));
                Toast.makeText(getApplicationContext(),newLocation.latitude+"",Toast.LENGTH_SHORT).show();
                gcords.replace(0,gcords.length(),"");
                gcords.append(newLocation.latitude+","+newLocation.longitude);
                mMap.getMapAsync(current);
                onMapReady(mMap.getMap());


            }
            @Override
            public void onMarkerDragStart(Marker marker) {
             /*   LatLng newLocation = marker.getPosition();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 15.0f));*/
            }
        });

    }


    public void sendInvitation(View v) {
        PhoneNumberSms.sendSms(getIntent().getStringExtra("SenderNum"),"Invitation Request:"+gcords,getApplicationContext());
    }

}
