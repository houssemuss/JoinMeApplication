package com.enseirb.joinme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hcherif.enseirb.com.joinmeapplication.R;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.util.StringTokenizer;

public class ArrangmentResponse extends AppCompatActivity implements OnMapReadyCallback {
    private   SupportMapFragment mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrangment_response);

        try {


            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
         //   if(etat) {
            mMap.getMapAsync(this);
            onMapReady(mMap.getMap());

           // }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public  void onMapReady(GoogleMap map) {

        String msg=getIntent().getStringExtra("senderMsg");
        StringTokenizer stmsg=new StringTokenizer(msg,":");
        stmsg.nextToken();
        String cords=stmsg.nextToken();
        StringTokenizer st=new StringTokenizer(cords,",");
        LatLng mylocation;
        try {

            mylocation= new LatLng(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "map non active", Toast.LENGTH_SHORT).show();
                return;
            }
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 13));
            map.addMarker(new MarkerOptions()
                    .title("Lieu de rendez-vous")
                    .position(mylocation));
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

     public void accept(View v){
       //  Toast.makeText(getApplicationContext(),getIntent().getStringExtra("senderNum"),Toast.LENGTH_LONG).show();
         PhoneNumberSms.sendSms(getIntent().getStringExtra("senderNum"),"Invitation Response : accepted",getApplicationContext());
     }

     public void dismiss(View v){
         PhoneNumberSms.sendSms(getIntent().getStringExtra("senderNum"),"Invitation Response : dismissed",getApplicationContext());
     }




}
