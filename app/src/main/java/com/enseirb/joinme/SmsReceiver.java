package com.enseirb.joinme;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jemai on 25/12/17.
 */

public class SmsReceiver extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();


    @Override
    public void onReceive(Context context, Intent intent) {


        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                    if(message.matches(".*:\\-?[0-9]+\\.[0-9]+,\\-?[0-9]+\\.[0-9]+")) {
                        if (message.contains("Request")) {
                            Intent intentResponse = new Intent(context, ArrangmentResponse.class);
                            intentResponse.putExtra("senderNum", senderNum);
                            intentResponse.putExtra("senderMsg", message);
                            context.startActivity(intentResponse);
                        }

                    }
                    else if(message.contains("Invitation Response")){
                        Toast.makeText(context,
                                "senderNum: " + senderNum + ", Response msg: " + message,Toast.LENGTH_LONG).show();

                    }

                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }

}
