package com.enseirb.joinme;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hcherif.enseirb.com.joinmeapplication.R;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberList extends AppCompatActivity {


    ListView listview ;
    List<Contact> contacts= new ArrayList<Contact>();
    SparseBooleanArray sparseBooleanArray ;
    List<Contact> selectedContacts;
    CustomListItem adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_list);
        contacts=getAllContacts();
        listview = (ListView)findViewById(R.id.listContact);
        adapter= new CustomListItem(getApplicationContext(), R.layout.customlisteitem, contacts );
        listview.setAdapter( new CustomListItem(getApplicationContext(), R.layout.customlisteitem, contacts ) );

    }


        public void broadcastInvitation(View v){
            //Toast.makeText(getApplicationContext(),"ecouter "+CustomListItem.selectedContacts.size(),Toast.LENGTH_LONG).show();
            selectedContacts=CustomListItem.selectedContacts;
            String cords=ServiceGPS.getCords();
            if(selectedContacts.size()>0) {
                if(!ServiceGPS.getCords().matches(".*Error.*")) {
                    for (Contact contact : selectedContacts) {
                        PhoneNumberSms.sendSms(contact.getPhone_number(), "Invitation Request:" +cords, getApplicationContext());
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"NO GPS CORDS FOUND",Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(getApplicationContext(),"Contact selection is required",Toast.LENGTH_SHORT).show();
            }

        }

        public void changePlaceBroadcast(View v){
            selectedContacts=CustomListItem.selectedContacts;
            if(selectedContacts.size()>0) {
                ArrayList<? extends Parcelable> myselectedContacts = new ArrayList<Contact>(selectedContacts);
                Intent intent = new Intent(getApplicationContext(), ChooseMapLocation.class);
                intent.putParcelableArrayListExtra("listContacts", myselectedContacts);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(),"Contact selection is required",Toast.LENGTH_SHORT).show();
            }

        }


        public  List<Contact> getAllContacts(){
            List<Contact> myContacts=new ArrayList<Contact>();

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while (phones.moveToNext())
            {
                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
              //  Toast.makeText(getApplicationContext(),name, Toast.LENGTH_LONG).show();
                Contact contact=new Contact();
                contact.setName(name);
                contact.setPhone_number(phoneNumber);
                myContacts.add(contact);
            }
            phones.close();
            return myContacts;
        }

}
