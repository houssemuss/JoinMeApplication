package com.enseirb.joinme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hcherif.enseirb.com.joinmeapplication.R;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneNumberSms extends AppCompatActivity {
    TextView text=null,value=null;
    StringBuffer chaine=new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_sms);
        value=(TextView)findViewById(R.id.resultat_value);
    }



    public void setPhoneNumber(View v) {
        Button button=(Button)v;
        putText(button,chaine);
        value.setText(chaine);

    }
    private void putText(Button b, StringBuffer chaine){
        char caractere=b.getText().charAt(0);
        switch(caractere){
            case 'c':
                chaine.replace(0,chaine.length(),"");
                value.setText("0");
                break;
            case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                chaine.append(caractere);

                break;
            case '+':
                if(chaine.length()==0 ){
                    chaine.append('+');
                }
                break;



        }
    }


    public void sendGpsData(View v){
        String cords=ServiceGPS.getCords();
       // Toast.makeText(getApplicationContext(), cords,Toast.LENGTH_SHORT).show();

        if(chaine.length()>0 ) {
            if(!cords.matches(".*Error.*")) {
                sendSms(new String(chaine), "Invitation Request:" + cords, getApplicationContext());
            }
            else{
                Toast.makeText(getApplicationContext(),"NO GPS CORDS FOUND",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"phone number is required",Toast.LENGTH_LONG).show();
        }
    }

    public void changeMapLocation(View v){
        Intent intent=new Intent(getApplicationContext(),ChooseMapLocation.class);
        if(chaine.length()>0) {
            intent.putExtra("SenderNum", new String(chaine));
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"phone number is required",Toast.LENGTH_LONG).show();
        }

    }


 public static  void sendSms(String phoneNumber,String msg, Context context){

     try {
         SmsManager smsManager = SmsManager.getDefault();
         smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
         Toast.makeText(context, "SMS Sent!",Toast.LENGTH_SHORT).show();
     } catch (Exception e) {

        // Toast.makeText(this.getApplicationContext(), "SMS failed!",Toast.LENGTH_SHORT).show();
         Toast.makeText(context, "SMS SENT FAILED"+e.getMessage(),Toast.LENGTH_SHORT).show();
         e.printStackTrace();
     }

 }




}
