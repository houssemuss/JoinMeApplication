package com.enseirb.joinme;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hcherif.enseirb.com.joinmeapplication.R;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_CONTACTS,Manifest.permission.INTERNET
            };
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }

        final Intent intent=new Intent(this,choice_window.class);
        final Intent intentService=new Intent(this,ServiceGPS.class);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

              startService(intentService);
              startActivity(intent);
                finish();
            }
        };
        new Timer().schedule(timerTask, 8000);
    }


    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        for (String permission : permissions){
            if(ActivityCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Information");
                alertDialog.setMessage(permission +"must be garanted");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        }
    }

}
